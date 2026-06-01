package dev.bookservice.repository.order;

import dev.bookservice.entity.order.Order;
import dev.bookservice.entity.order.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Order} в базе данных.
 * <p>
 * Предоставляет методы для поиска, создания и обновления заказов.
 */
public interface OrderRepository {
    /**
     * Ищет заказ по уникальному идентификатору с проверкой принадлежности пользователю.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью только если заказ существует
     * и принадлежит указанному пользователю. Используется для обеспечения изоляции данных
     * и предотвращения доступа к чужим заказам.
     * <p>
     * Возвращает пустой {@link Optional#empty()}, если:
     * <ul>
     *     <li>Заказ с указанным {@code orderId} не найден;</li>
     *     <li>Заказ найден, но принадлежит другому пользователю ({@code userId}).</li>
     * </ul>
     *
     * @param orderId уникальный идентификатор заказа
     * @param userId  уникальный идентификатор владельца заказа
     * @return {@code Optional<Order>} с результатом поиска
     * @see #findAllOrdersByUserId(Long)
     */
    Optional<Order> getOrderById(Long orderId, Long userId);

    /**
     * Создает новый заказ в базе данных.
     * <p>
     * Сохраняет сущность {@link Order} и возвращает сгенерированный системой
     * уникальный идентификатор созданного заказа.
     *
     * @param orderEntity сущность заказа, содержащая номер, статус и дату создания
     * @param userId      уникальный идентификатор пользователя, создающего заказ
     * @return уникальный идентификатор ({@link Long}) созданного заказа
     */
    Long createOrder(Order orderEntity, Long userId);

    /**
     * Обновляет статус и итоговую сумму существующего заказа.
     * <p>
     * Используется после успешного расчета стоимости всех позиций заказа
     * для фиксации финальной суммы и перевода заказа в следующий статус
     * (например, из {@code CREATING} в {@code PROCESSING}).
     *
     * @param orderId    уникальный идентификатор заказа
     * @param totalPrice итоговая сумма заказа ({@link BigDecimal})
     * @param status     новый статус заказа
     */
    void updateOrderStatusByOrderId(Long orderId, BigDecimal totalPrice, Status status);

    /**
     * Возвращает список всех заказов, принадлежащих указанному пользователю.
     * <p>
     * Выполняет поиск по внешнему ключу пользователя. Если заказы не найдены,
     * возвращается пустой список.
     * <p>
     * Результаты могут быть отсортированы по дате создания (от новых к старым)
     * на уровне запроса к базе данных.
     *
     * @param userId уникальный идентификатор пользователя
     * @return список сущностей {@link Order}, принадлежащих данному пользователю
     * @see #getOrderById(Long, Long)
     */
    List<Order> findAllOrdersByUserId(Long userId);
}