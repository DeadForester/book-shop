package dev.bookservice.repository.order_item;

import dev.bookservice.entity.order.OrderItem;

import java.util.List;

/**
 * Репозиторий для работы с сущностями {@link OrderItem} в базе данных.
 * <p>
 * Предоставляет методы для поиска и сохранения позиций заказа.
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

    /**
     * Создает новую позицию заказа в базе данных.
     * <p>
     * Сохраняет сущность {@link OrderItem}, связывая её с конкретным заказом
     * и книгой (товаром) через их идентификаторы.
     *
     * @param newEntity         сущность позиции заказа, содержащая количество и другие атрибуты
     * @param bookIdByOrderItem уникальный идентификатор книги, добавляемой в заказ
     * @param orderId           уникальный идентификатор родительского заказа
     */
    void createOrderItem(OrderItem newEntity, Long bookIdByOrderItem, Long orderId);
}
