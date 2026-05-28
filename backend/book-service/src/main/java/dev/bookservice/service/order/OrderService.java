package dev.bookservice.service.order;

import dev.bookservice.entity.order.Order;
import dev.bookservice.exception.not_found.OrderNotFoundException;
import dev.bookservice.repository.order.OrderRepository;
import dev.bookservice.service.order_item.OrderItemService;
import dev.bookservice.web.dto.order.GetOrderById;
import dev.bookservice.web.dto.order_item.GetOrderItemByOrderId;
import dev.bookservice.web.mapper.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления заказами.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final OrderMapper orderMapper;

    /**
     * Получает полную информацию о заказе по его уникальному идентификатору.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает сущность {@link Order} через {@link OrderRepository#getOrderById(Long)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи логирует предупреждение уровня {@code WARN} и выбрасывает {@link OrderNotFoundException};</li>
     *     <li>Запрашивает список позиций заказа (DTO) через {@link OrderItemService#getOrderItemsByOrderId(Long)};</li>
     *     <li>Преобразует сущность заказа и список позиций в итоговый DTO через {@link OrderMapper#toDtoOrderById(Order, List)};</li>
     *     <li>Возвращает полученный DTO.</li>
     * </ol>
     *
     * @param orderId уникальный идентификатор заказа
     * @return DTO {@link GetOrderById}, содержащий информацию о заказе и его позициях
     * @throws OrderNotFoundException если заказ с указанным {@code orderId} не найден
     * @see OrderRepository#getOrderById(Long)
     * @see OrderItemService#getOrderItemsByOrderId(Long)
     * @see OrderMapper#toDtoOrderById(Order, List)
     */
    public GetOrderById getOrderById(Long orderId) {
        log.debug("Получение orderId= {} из БД", orderId);
        Order order = orderRepository.getOrderById(orderId).orElseThrow(
                () -> {
                    log.warn("Ошибка при получении order по id = {}. Такого айди не существует", orderId);
                    return new OrderNotFoundException("Ошибка при получении Заказа. Данного заказа не существует");
                }
        );

        log.debug("Получение списка всех книг для заказа");

        List<GetOrderItemByOrderId> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

        return orderMapper.toDtoOrderById(order, orderItems);
    }
}
