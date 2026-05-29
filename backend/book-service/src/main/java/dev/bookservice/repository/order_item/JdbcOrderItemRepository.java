package dev.bookservice.repository.order_item;

import dev.bookservice.entity.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcOrderItemRepository implements OrderItemRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        String sql = """
                SELECT oi.ORDER_ITEM_ID
                    , oi.QUANTITY
                    , oi.CREATED_AT
                    , oi.MODIFIED_AT
                FROM ORDER_ITEMS oi
                WHERE oi.ORDER_ID = ?
                """;

        List<OrderItem> result = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                orderId
        );

        return result.isEmpty() ? Collections.emptyList() : result;
    }

    @Override
    public void createOrderItem(OrderItem newEntity, Long bookIdByOrderItem, Long orderId) {
        String sql = """
                INSERT INTO ORDER_ITEMS (BOOK_ID, ORDER_ID, QUANTITY, CREATED_AT)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                bookIdByOrderItem,
                orderId,
                newEntity.getQuantity(),
                newEntity.getCreatedAt()
        );
    }

    private OrderItem mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return OrderItem.builder()
                .orderItemId(rs.getLong("ORDER_ITEM_ID"))
                .quantity(rs.getInt("QUANTITY"))
                .createdAt(timeOfNullable(rs.getTimestamp("CREATED_AT")))
                .modifiedAt(timeOfNullable(rs.getTimestamp("MODIFIED_AT")))
                .build();
    }

    private LocalDateTime timeOfNullable(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toLocalDateTime();
        }
    }
}
