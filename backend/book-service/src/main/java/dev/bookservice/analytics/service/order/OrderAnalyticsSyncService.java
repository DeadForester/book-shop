package dev.bookservice.analytics.service.order;

import dev.bookservice.web.dto.order.OrderFactDto;
import dev.bookservice.web.dto.order.OrderItemFactDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис синхронизации данных о заказах из PostgreSQL в ClickHouse.
 * <p>
 * Выполняет ETL-процесс: извлекает данные о заказах и их позициях
 * из основной БД, обогащает их информацией о книгах, авторах и издательствах,
 * и загружает в аналитическое хранилище ClickHouse.
 * <p>
 * Использует прямые SQL-запросы для чтения из PostgreSQL, чтобы не зависеть
 * от ограничений Spring Data JDBC и иметь возможность делать JOIN-запросы.
 *
 * @see JdbcTemplate
 */
@Service
@Slf4j
public class OrderAnalyticsSyncService {

    private final JdbcTemplate postgresJdbcTemplate;


    private final JdbcTemplate clickHouseJdbcTemplate;


    public OrderAnalyticsSyncService(
            @Qualifier("clickHouseJdbcTemplate") JdbcTemplate clickHouseJdbcTemplate,
            @Qualifier("postgresJdbcTemplate")
            JdbcTemplate postgresJdbcTemplate) {
        this.clickHouseJdbcTemplate = clickHouseJdbcTemplate;
        this.postgresJdbcTemplate = postgresJdbcTemplate;
    }

    private static final String EXTRACT_ORDERS_SQL = """
            SELECT 
                o.order_id,
                o.order_number,
                o.user_id,
                u.email AS user_email,
                o.created_at AS order_date,
                o.status::text AS status,
                o.total_price,
                o.created_at,
                o.modified_at
            FROM orders o
            JOIN usrs u ON o.user_id = u.user_id
            WHERE o.created_at > ?
            ORDER BY o.created_at
            """;

    private static final String EXTRACT_ORDER_ITEMS_SQL = """
            SELECT 
                oi.order_item_id,
                oi.order_id,
                oi.quantity,
                oi.created_at AS order_item_date,
                o.created_at AS order_date,
                o.status::text AS order_status,
                o.user_id,
                b.book_id,
                b.title AS book_title,
                b.genre,
                b.amount AS price,
                p.publisher_id,
                p.name AS publisher_name
            FROM order_items oi
            JOIN orders o ON oi.order_id = o.order_id
            JOIN books b ON oi.book_id = b.book_id
            LEFT JOIN publishers p ON b.publisher_id = p.publisher_id
            WHERE oi.order_id = ANY(?)
            """;

    private static final String EXTRACT_AUTHORS_SQL = """
            SELECT 
                ba.book_id,
                STRING_AGG(a.firstname || ' ' || a.surname, ', ') AS author_names
            FROM authors_books ba
            JOIN authors a ON ba.author_id = a.author_id
            WHERE ba.book_id = ANY(?)
            GROUP BY ba.book_id
            """;

    private static final String INSERT_ORDER_SQL = """
            INSERT INTO book_shop_analyst.orders_fact 
            (order_id, order_number, user_id, user_email, order_date, status, 
             total_price, items_count, books_count, total_books, created_at, modified_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String INSERT_ITEM_SQL = """
            INSERT INTO book_shop_analyst.order_items_fact 
            (order_item_id, order_id, order_date, user_id, order_status,
             book_id, book_title, genre, author_names, publisher_id, publisher_name,
             quantity, price, total_amount)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    /**
     * Выполняет полную синхронизацию всех заказов в ClickHouse.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Извлекает все заказы из PostgreSQL с информацией о пользователях;</li>
     *     <li>Для каждого заказа извлекает позиции с данными о книгах;</li>
     *     <li>Обогащает данные информацией об авторах и издательствах;</li>
     *     <li>Загружает данные в ClickHouse batch-операциями.</li>
     * </ol>
     */
    public void syncAllOrders() {
        log.info("Начало полной синхронизации заказов в ClickHouse");
        syncOrdersSince(LocalDateTime.of(2020, 1, 1, 0, 0));
    }

    /**
     * Выполняет инкрементальную синхронизацию заказов, созданных после указанной даты.
     *
     * @param since дата, начиная с которой необходимо синхронизировать заказы
     */
    public void syncOrdersSince(LocalDateTime since) {
        log.info("Синхронизация заказов с {}", since);

        List<OrderRow> orders = postgresJdbcTemplate.query(
                EXTRACT_ORDERS_SQL,
                (rs, rowNum) -> new OrderRow(
                        rs.getLong("order_id"),
                        rs.getString("order_number"),
                        rs.getLong("user_id"),
                        rs.getString("user_email"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getBigDecimal("total_price"),
                        timeOfNullable(rs.getTimestamp("created_at")),
                        timeOfNullable(rs.getTimestamp("modified_at"))
                ),
                Timestamp.valueOf(since)
        );

        if (orders.isEmpty()) {
            log.info("Новых заказов для синхронизации не найдено");
            return;
        }

        List<Long> orderIds = orders.stream().map(o -> o.orderId).toList();

        List<OrderItemRow> items = postgresJdbcTemplate.query(
                EXTRACT_ORDER_ITEMS_SQL,
                (rs, rowNum) -> new OrderItemRow(
                        rs.getLong("order_item_id"),
                        rs.getLong("order_id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getString("order_status"),
                        rs.getLong("user_id"),
                        rs.getLong("book_id"),
                        rs.getString("book_title"),
                        rs.getString("genre"),
                        rs.getBigDecimal("price"),
                        rs.getLong("publisher_id"),
                        rs.getString("publisher_name")
                ),
                (Object) orderIds.toArray(new Long[0])
        );

        List<Long> bookIds = items.stream().map(i -> i.bookId).distinct().toList();
        var authorsByBook = postgresJdbcTemplate.query(
                EXTRACT_AUTHORS_SQL,
                (rs, rowNum) -> new AuthorRow(
                        rs.getLong("book_id"),
                        rs.getString("author_names")
                ),
                (Object) bookIds.toArray(new Long[0])
        ).stream().collect(Collectors.toMap(a -> a.bookId, a -> a.authorNames));

        var itemsByOrder = items.stream()
                .collect(Collectors.groupingBy(i -> i.orderId));

        List<OrderFactDto> orderFacts = new ArrayList<>();
        List<OrderItemFactDto> itemFacts = new ArrayList<>();

        for (OrderRow order : orders) {
            List<OrderItemRow> orderItems = itemsByOrder.getOrDefault(order.orderId, List.of());
            int totalBooks = orderItems.stream().mapToInt(i -> i.quantity).sum();
            int booksCount = (int) orderItems.stream().map(i -> i.bookId).distinct().count();

            orderFacts.add(new OrderFactDto(
                    order.orderId, order.orderNumber, order.userId, order.userEmail,
                    order.orderDate, order.status, order.totalPrice,
                    orderItems.size(), booksCount, totalBooks,
                    order.createdAt, order.modifiedAt
            ));

            for (OrderItemRow item : orderItems) {
                String authors = authorsByBook.getOrDefault(item.bookId, "Неизвестный автор");
                BigDecimal totalAmount = item.price.multiply(BigDecimal.valueOf(item.quantity));

                itemFacts.add(new OrderItemFactDto(
                        item.orderItemId, item.orderId, item.orderDate, item.userId,
                        item.orderStatus, item.bookId, item.bookTitle, item.genre,
                        authors, item.publisherId, item.publisherName,
                        item.quantity, item.price, totalAmount
                ));
            }
        }

        insertOrdersBatch(orderFacts);
        insertItemsBatch(itemFacts);

        log.info("Синхронизация завершена. Заказов: {}, позиций: {}",
                orderFacts.size(), itemFacts.size());
    }

    private LocalDateTime timeOfNullable(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toLocalDateTime();
        }
    }

    private void insertOrdersBatch(List<OrderFactDto> records) {
        if (records.isEmpty()) return;

        List<Object[]> batch = records.stream().map(r -> new Object[]{
                r.orderId(), r.orderNumber(), r.userId(), r.userEmail(),
                toTimestamp(r.orderDate()), r.status(), r.totalPrice(),
                r.itemsCount(), r.booksCount(), r.totalBooks(),
                toTimestamp(r.createdAt()), toTimestamp(r.modifiedAt())
        }).toList();

        clickHouseJdbcTemplate.batchUpdate(INSERT_ORDER_SQL, batch);
    }

    private void insertItemsBatch(List<OrderItemFactDto> records) {
        if (records.isEmpty()) return;

        List<Object[]> batch = records.stream().map(r -> new Object[]{
                r.orderItemId(), r.orderId(), toTimestamp(r.orderDate()),
                r.userId(), r.orderStatus(), r.bookId(), r.bookTitle(),
                r.genre(), r.authorNames(), r.publisherId(), r.publisherName(),
                r.quantity(), r.price(), r.totalAmount()
        }).toList();

        clickHouseJdbcTemplate.batchUpdate(INSERT_ITEM_SQL, batch);
    }

    private Timestamp toTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
    }

    private record OrderRow(Long orderId, String orderNumber, Long userId, String userEmail,
                            LocalDateTime orderDate, String status, BigDecimal totalPrice,
                            LocalDateTime createdAt, LocalDateTime modifiedAt) {
    }

    private record OrderItemRow(Long orderItemId, Long orderId, Integer quantity,
                                LocalDateTime orderDate, String orderStatus, Long userId,
                                Long bookId, String bookTitle, String genre, BigDecimal price,
                                Long publisherId, String publisherName) {
    }

    private record AuthorRow(Long bookId, String authorNames) {
    }
}
