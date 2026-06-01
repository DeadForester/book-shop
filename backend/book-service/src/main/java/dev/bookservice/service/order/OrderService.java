package dev.bookservice.service.order;

import dev.bookservice.entity.order.Order;
import dev.bookservice.entity.order.Status;
import dev.bookservice.exception.bad_request.BadRequestException;
import dev.bookservice.exception.bad_request.CreateOrderException;
import dev.bookservice.exception.bad_request.DifferentTotalSumException;
import dev.bookservice.exception.not_found.OrderNotFoundException;
import dev.bookservice.repository.order.OrderRepository;
import dev.bookservice.service.order_item.OrderItemService;
import dev.bookservice.service.user.CurrentUserService;
import dev.bookservice.web.dto.order.GetOrderById;
import dev.bookservice.web.dto.order.PostOrder;
import dev.bookservice.web.dto.order_item.GetOrderItemByOrderId;
import dev.bookservice.web.dto.order_item.PostOrderItem;
import dev.bookservice.web.mapper.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Сервис для управления заказами.
 * <p>
 * Предоставляет бизнес-логику для создания заказов, проверки целостности данных,
 * получения информации о заказах текущего пользователя и агрегации связанных сущностей.
 * <p>
 * Использует {@link CurrentUserService} для определения контекста безопасности
 * и обеспечения изоляции данных между пользователями.
 *
 * @see OrderRepository
 * @see OrderItemService
 * @see OrderMapper
 * @see CurrentUserService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final OrderMapper orderMapper;
    private final CurrentUserService currentUserService;
    private final static ZoneId ZONE_ID = ZoneId.of("Europe/Moscow");

    /**
     * Получает полную информацию о заказе по его уникальному идентификатору.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Получает идентификатор текущего пользователя через {@link CurrentUserService#getCurrentUserId()};</li>
     *     <li>Запрашивает сущность {@link Order} через {@link OrderRepository#getOrderById(Long, Long)} с проверкой принадлежности;</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи логирует предупреждение уровня {@code WARN} и выбрасывает {@link OrderNotFoundException};</li>
     *     <li>Запрашивает список позиций заказа (DTO) через {@link OrderItemService#getOrderItemsByOrderId(Long)};</li>
     *     <li>Преобразует сущность заказа и список позиций в итоговый DTO через {@link OrderMapper#toDtoOrderById(Order, List)};</li>
     *     <li>Возвращает полученный DTO.</li>
     * </ol>
     *
     * @param orderId уникальный идентификатор заказа
     * @return DTO {@link GetOrderById}, содержащий информацию о заказе и его позициях
     * @throws OrderNotFoundException если заказ с указанным {@code orderId} не найден или не принадлежит текущему пользователю
     * @see OrderRepository#getOrderById(Long, Long)
     * @see OrderItemService#getOrderItemsByOrderId(Long)
     * @see OrderMapper#toDtoOrderById(Order, List)
     */
    public GetOrderById getOrderById(Long orderId) {

        if (orderId == null) {
            throw new BadRequestException("Укажите верный параметр");
        }

        Long currentUserId = currentUserService.getCurrentUserId();

        log.debug("Получение orderId = {} из БД для пользователя = {}", orderId, currentUserId);

        Order order = orderRepository.getOrderById(orderId, currentUserId).orElseThrow(() -> {
            log.warn("Ошибка при получении order по id = {}. Такого айди не существует", orderId);
            return new OrderNotFoundException("Ошибка при получении Заказа. Данного заказа не существует");
        });

        log.debug("Получение списка всех книг для заказа");

        List<GetOrderItemByOrderId> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

        return orderMapper.toDtoOrderById(order, orderItems);
    }

    /**
     * Создает новый заказ на основе предоставленных данных.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Получает идентификатор текущего пользователя через {@link CurrentUserService#getCurrentUserId()};</li>
     *     <li>Проверяет список товаров на {@code null} или пустоту; при отсутствии товаров выбрасывает {@link CreateOrderException};</li>
     *     <li>Генерирует уникальный номер заказа и создает сущность {@link Order} со статусом {@code CREATING} через {@link #createOrderEntity(LocalDateTime, Long)};</li>
     *     <li>Сохраняет позиции заказа и рассчитывает их фактическую сумму через {@link OrderItemService#createOrderItems(LocalDateTime, List, Long)};</li>
     *     <li>Сравнивает рассчитанную сумму с суммой, переданной клиентом ({@code postOrder.getTotalPrice()});</li>
     *     <li>При несовпадении сумм логирует ошибку и выбрасывает {@link DifferentTotalSumException};</li>
     *     <li>Обновляет статус заказа на {@code PROCESSING} и его конечную стоимость через {@link #updateOrderStatusByOrderId(Long, BigDecimal, Status)};</li>
     *     <li>Возвращает полную информацию о созданном заказе через {@link #getOrderById(Long)}.</li>
     * </ol>
     *
     * @param postOrder DTO, содержащий список позиций заказа и ожидаемую общую сумму
     * @return DTO {@link GetOrderById} с данными созданного заказа
     * @throws CreateOrderException       если список товаров пуст или {@code null}
     * @throws DifferentTotalSumException если расчетная сумма не совпадает с переданной
     * @see #createOrderEntity(LocalDateTime, Long)
     * @see OrderItemService#createOrderItems(LocalDateTime, List, Long)
     * @see #updateOrderStatusByOrderId(Long, BigDecimal, Status)
     */
    public GetOrderById createOrder(PostOrder postOrder) {

        if (postOrder == null) {
            throw new BadRequestException("Укажите верный параметр");
        }

        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        Long currentUserId = currentUserService.getCurrentUserId();


        List<PostOrderItem> newOrderItems = postOrder.getOrderItems();

        if (newOrderItems == null || newOrderItems.isEmpty()) {
            throw new CreateOrderException("Ошибка при создании Заказа. Список товаров пуст");
        }

        Long orderId = createOrderEntity(now, currentUserId);

        BigDecimal totalPrice = orderItemService.createOrderItems(now, newOrderItems, orderId);

        if (!Objects.equals(postOrder.getTotalPrice(), totalPrice)) {
            log.error("Сумма на беке и фронте различаются");
            throw new DifferentTotalSumException("Ошибка суммы всего заказа. Сумма на фронте и беке различается");
        }

        updateOrderStatusByOrderId(orderId, totalPrice, Status.PROCESSING);

        return getOrderById(orderId);
    }

    /**
     * Получает список всех заказов текущего аутентифицированного пользователя.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Получает идентификатор текущего пользователя через {@link CurrentUserService#getCurrentUserId()};</li>
     *     <li>Запрашивает список сущностей {@link Order} через {@link OrderRepository#findAllOrdersByUserId(Long)};</li>
     *     <li>При отсутствии записей возвращает пустой неизменяемый список;</li>
     *     <li>Для каждого заказа:
     *         <ul>
     *             <li>Загружает список позиций через {@link OrderItemService#getOrderItemsByOrderId(Long)};</li>
     *             <li>Преобразует заказ и его позиции в DTO {@link GetOrderById} через {@link OrderMapper#toDtoOrderById(Order, List)}.</li>
     *         </ul>
     *     </li>
     *     <li>Возвращает список преобразованных DTO.</li>
     * </ol>
     * <p>
     * <strong>Примечание:</strong> Текущая реализация выполняет N+1 запросов к базе данных
     * (один запрос на получение списка заказов + по одному запросу на загрузку позиций для каждого заказа).
     * При высокой нагрузке рекомендуется оптимизировать через {@code JOIN FETCH} или кэширование.
     *
     * @return список DTO {@link GetOrderById} с информацией о заказах текущего пользователя
     * @see CurrentUserService#getCurrentUserId()
     * @see OrderRepository#findAllOrdersByUserId(Long)
     * @see OrderItemService#getOrderItemsByOrderId(Long)
     */
    public List<GetOrderById> findAllOrdersByUser() {
        Long currentUserId = currentUserService.getCurrentUserId();

        List<Order> allOrdersByUserId = orderRepository.findAllOrdersByUserId(currentUserId);

        if (allOrdersByUserId.isEmpty()) {
            return Collections.emptyList();
        } else {
            return allOrdersByUserId.stream().map(order -> {
                List<GetOrderItemByOrderId> orderItems = orderItemService.getOrderItemsByOrderId(order.getOrderId());
                return orderMapper.toDtoOrderById(order, orderItems);
            }).toList();
        }
    }

    /**
     * Создает и сохраняет сущность заказа с генерацией уникального номера.
     * <p>
     * Номер заказа формируется как комбинация текущей даты (формат {@code ddMMyyyy})
     * и случайного четырехзначного числа (от 1000 до 9999).
     *
     * @param now           текущее время создания заказа
     * @param currentUserId уникальный идентификатор пользователя, создающего заказ
     * @return уникальный идентификатор созданного заказа
     * @see OrderRepository#createOrder(Order, Long)
     */
    private Long createOrderEntity(LocalDateTime now, Long currentUserId) {
        int number = new Random().nextInt(1000, 10000);

        String orderNumber = now.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + number;

        Order order = Order.builder().orderNumber(orderNumber).status(Status.CREATING).createdAt(now).build();

        return orderRepository.createOrder(order, currentUserId);
    }

    /**
     * Обновляет статус и итоговую сумму существующего заказа.
     * <p>
     * Делегирует вызов в {@link OrderRepository} для выполнения UPDATE-запроса к базе данных.
     *
     * @param orderId    уникальный идентификатор заказа
     * @param totalPrice итоговая сумма заказа ({@link BigDecimal})
     * @param status     новый статус заказа
     * @see OrderRepository#updateOrderStatusByOrderId(Long, BigDecimal, Status)
     */
    private void updateOrderStatusByOrderId(Long orderId, BigDecimal totalPrice, Status status) {
        orderRepository.updateOrderStatusByOrderId(orderId, totalPrice, status);
    }
}