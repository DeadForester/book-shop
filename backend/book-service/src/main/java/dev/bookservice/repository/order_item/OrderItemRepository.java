package dev.bookservice.repository.order_item;

import dev.bookservice.entity.order.OrderItem;

import java.util.List;

/**
 * Репозиторий для работы с сущностями {@link OrderItem} в базе данных.
 */
public interface OrderItemRepository {

    /**
     * Возвращает список всех позиций, связанных с указанным заказом.
     * <p>
     * Выполняет поиск по внешнему ключу заказа. Если позиции не найдены,
     * возвращается пустой список.
     *
     * @param orderId уникальный идентификатор родительского заказа
     * @return список сущностей {@link OrderItem}, принадлежащих данному заказу
     */
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
}
