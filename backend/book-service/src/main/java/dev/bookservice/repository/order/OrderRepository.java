package dev.bookservice.repository.order;

import dev.bookservice.entity.order.Order;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Order} в базе данных.
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
}
