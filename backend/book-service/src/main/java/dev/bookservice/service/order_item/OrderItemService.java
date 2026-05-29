package dev.bookservice.service.order_item;

import dev.bookservice.entity.order.OrderItem;
import dev.bookservice.exception.not_found.BookNotFoundException;
import dev.bookservice.repository.order_item.OrderItemRepository;
import dev.bookservice.service.book.BookService;
import dev.bookservice.web.dto.book.GetBookByOrderItem;
import dev.bookservice.web.dto.order_item.GetOrderItemByOrderId;
import dev.bookservice.web.dto.order_item.PostOrderItem;
import dev.bookservice.web.mapper.order_item.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для управления позициями заказа (Order Items).
 * <p>
 * Предоставляет бизнес-логику для получения списка товаров в заказе,
 * агрегируя данные о позиции заказа и связанной с ней книге,
 * а также для создания новых позиций при оформлении заказа.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final BookService bookService;
    private final OrderItemMapper orderItemMapper;

    /**
     * Получает список позиций заказа по идентификатору родительского заказа.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает список сущностей {@link OrderItem} через {@link OrderItemRepository#getOrderItemsByOrderId(Long)};</li>
     *     <li>Проверяет результат на пустоту;</li>
     *     <li>При отсутствии записей логирует ошибку уровня {@code ERROR} и выбрасывает {@link BookNotFoundException};</li>
     *     <li>Для каждой позиции выполняет преобразование в DTO:</li>
     *     <ul>
     *         <li>Получает информацию о книге через {@link BookService#getBookByOrderItemId(Long)};</li>
     *         <li>Маппит позицию и книгу в DTO {@link GetOrderItemByOrderId} через {@link OrderItemMapper#toDtoOrderItemByOrderId(OrderItem, GetBookByOrderItem)}.</li>
     *     </ul>
     *     <li>Возвращает список полученных DTO.</li>
     * </ol>
     *
     * @param orderId уникальный идентификатор заказа
     * @return список DTO {@link GetOrderItemByOrderId}, содержащих информацию о товарах в заказе
     * @throws BookNotFoundException если для указанного {@code orderId} не найдено ни одной позиции заказа
     * @see OrderItemRepository#getOrderItemsByOrderId(Long)
     * @see BookService#getBookByOrderItemId(Long)
     * @see OrderItemMapper#toDtoOrderItemByOrderId(OrderItem, GetBookByOrderItem)
     */
    public List<GetOrderItemByOrderId> getOrderItemsByOrderId(Long orderId) {
        log.debug("Получение orderItems по orderId = {}", orderId);
        List<OrderItem> orderItemsByOrderId = orderItemRepository.getOrderItemsByOrderId(orderId);

        if (orderItemsByOrderId.isEmpty()) {
            log.error("Ошибка при получении книг по заказу = {}", orderId);
            throw new BookNotFoundException("Книги по заказу " + orderId + " не найдены");
        }

        return orderItemsByOrderId.stream().map(this::getOrderItemByOrderId).toList();
    }

    /**
     * Создает позиции заказа на основе входных данных и рассчитывает общую сумму.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Инициализирует общую сумму заказа нулевым значением;</li>
     *     <li>Для каждой позиции из списка {@code orderItems}:
     *         <ul>
     *             <li>Извлекает ID книги и количество;</li>
     *             <li>Получает актуальную цену книги через {@link #getSumOrderItem(Long)};</li>
     *             <li>Рассчитывает стоимость позиции (цена * количество);</li>
     *             <li>Создает новую сущность {@link OrderItem} в БД через {@link #createOrderItem(LocalDateTime, PostOrderItem, Long)};</li>
     *             <li>Добавляет стоимость позиции к общей сумме.</li>
     *         </ul>
     *     </li>
     *     <li>Возвращает итоговую сумму всех позиций.</li>
     * </ol>
     *
     * @param now        текущее время создания записей (для поля {@code createdAt})
     * @param orderItems список DTO {@link PostOrderItem}, содержащих ID книг и количество
     * @param orderId    уникальный идентификатор родительского заказа
     * @return общая сумма заказа ({@link BigDecimal})
     * @see #getSumOrderItem(Long)
     * @see #createOrderItem(LocalDateTime, PostOrderItem, Long)
     */
    public BigDecimal createOrderItems(LocalDateTime now, List<PostOrderItem> orderItems, Long orderId) {
        BigDecimal doubleTotalSum = BigDecimal.ZERO;

        for (PostOrderItem orderItem : orderItems) {
            BigDecimal price = getSumOrderItem(
                    getBookIdByOrderItem(orderItem)
            );

            BigDecimal quantity = getQuantityOrderItem(orderItem);

            createOrderItem(now, orderItem, orderId);

            doubleTotalSum = doubleTotalSum.add(price.multiply(quantity));
        }

        return doubleTotalSum;
    }

    /**
     * Преобразует сущность позиции заказа в DTO с информацией о книге.
     * <p>
     * Внутренний вспомогательный метод, используемый для маппинга элементов списка.
     *
     * @param orderItem сущность позиции заказа
     * @return DTO {@link GetOrderItemByOrderId}
     */
    private GetOrderItemByOrderId getOrderItemByOrderId(OrderItem orderItem) {
        GetBookByOrderItem book = bookService.getBookByOrderItemId(orderItem.getOrderItemId());
        return orderItemMapper.toDtoOrderItemByOrderId(orderItem, book);
    }

    /**
     * Получает стоимость одной единицы товара по его идентификатору.
     * <p>
     * Делегирует запрос в {@link BookService}.
     *
     * @param bookId идентификатор книги
     * @return цена книги ({@link BigDecimal})
     */
    private BigDecimal getSumOrderItem(Long bookId) {
        return bookService.getBookAmountByBookId(bookId);
    }

    /**
     * Извлекает идентификатор книги из DTO позиции заказа.
     *
     * @param orderItem DTO позиции заказа
     * @return ID книги ({@link Long})
     */
    private Long getBookIdByOrderItem(PostOrderItem orderItem) {
        return orderItem.getBook().getBookId();
    }

    /**
     * Преобразует количество товара из {@link Integer} в {@link BigDecimal}.
     * <p>
     * Необходимо для корректного арифметического расчета стоимости.
     *
     * @param orderItem DTO позиции заказа
     * @return количество в формате {@link BigDecimal}
     */
    private BigDecimal getQuantityOrderItem(PostOrderItem orderItem) {
        return new BigDecimal(orderItem.getQuantity());
    }

    /**
     * Создает и сохраняет новую позицию заказа в базе данных.
     * <p>
     * Формирует сущность {@link OrderItem} с помощью билдера, устанавливает
     * время создания и делегирует сохранение в репозиторий вместе с ID книги и заказа.
     *
     * @param now       время создания записи
     * @param orderItem DTO с данными о количестве и книге
     * @param orderId   ID родительского заказа
     * @see OrderItemRepository#createOrderItem(OrderItem, Long, Long)
     */
    private void createOrderItem(LocalDateTime now, PostOrderItem orderItem, Long orderId) {
        OrderItem newEntity = OrderItem.builder()
                .quantity(orderItem.getQuantity())
                .createdAt(now)
                .build();

        orderItemRepository.createOrderItem(
                newEntity,
                getBookIdByOrderItem(orderItem),
                orderId
        );
    }
}
