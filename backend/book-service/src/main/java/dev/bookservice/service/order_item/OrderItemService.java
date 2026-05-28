package dev.bookservice.service.order_item;

import dev.bookservice.entity.order.OrderItem;
import dev.bookservice.exception.not_found.BookNotFoundException;
import dev.bookservice.repository.order_item.OrderItemRepository;
import dev.bookservice.service.book.BookService;
import dev.bookservice.web.dto.book.GetBookByOrderItem;
import dev.bookservice.web.dto.order_item.GetOrderItemByOrderId;
import dev.bookservice.web.mapper.order_item.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления позициями заказа (Order Items).
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
}
