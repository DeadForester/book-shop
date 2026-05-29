package dev.bookservice.repository.order;

import dev.bookservice.entity.order.Order;
import dev.bookservice.entity.order.Status;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Order} в базе данных.
 * <p>
 * Предоставляет методы для поиска, создания и обновления заказов.
 */
public interface OrderRepository {

    /**
     * Ищет заказ по уникальному идентификатору.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@link Optional#empty()},
     * если заказ с указанным идентификатором не найден.
     *
     * @param orderId уникальный идентификатор заказа
     * @return {@code Optional<Order>} с результатом поиска
     */
    Optional<Order> getOrderById(Long orderId);

    /**
     * Создает новый заказ в базе данных.
     * <p>
     * Сохраняет сущность {@link Order} и возвращает сгенерированный системой
     * уникальный идентификатор созданного заказа.
     *
     * @param orderEntity сущность заказа, содержащая номер, статус и дату создания
     * @return уникальный идентификатор ({@link Long}) созданного заказа
     */
    Long createOrder(Order orderEntity);

    /**
     * Обновляет статус и итоговую сумму существующего заказа.
     * <p>
     * Используется после успешного расчета стоимости всех позиций заказа
     * для фиксации финальной суммы и перевода заказа в следующий статус
     * (например, из {@code CREATING} в {@code PROCESSING}).
     *
     * @param orderId уникальный идентификатор заказа
     * @param totalPrice итоговая сумма заказа ({@link BigDecimal})
     * @param status новый статус заказа
     */
    void updateOrderStatusByOrderId(Long orderId, BigDecimal totalPrice, Status status);
}
