package dev.bookservice.web.controller.order;

import dev.bookservice.exception.not_found.OrderNotFoundException;
import dev.bookservice.service.order.OrderService;
import dev.bookservice.web.dto.order.GetOrderById;
import dev.bookservice.web.dto.order.PostOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления запросами, связанными с заказами.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения и создания заказов. Все эндпоинты имеют базовый путь
 * {@code /api/v1/orders}.
 *
 * @see RestController
 * @see RequestMapping
 * @see OrderService
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Обрабатывает GET-запрос на получение детальной информации о заказе.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/orders/{orderId}}
     * <p>
     * <strong>Параметры запроса:</strong>
     * <table border="1" cellpadding="5" cellspacing="0">
     *     <tr>
     *         <th>Параметр</th>
     *         <th>Расположение</th>
     *         <th>Обязательный</th>
     *         <th>Описание</th>
     *     </tr>
     *     <tr>
     *         <td>{@code orderId}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор заказа (положительное число типа {@link Long})</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — заказ найден, тело ответа содержит {@link GetOrderById};</li>
     *     <li>{@code 404 Not Found} — заказ с указанным идентификатором не найден;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     *
     * @param orderId уникальный идентификатор запрашиваемого заказа
     * @return DTO {@link GetOrderById} с полной информацией о заказе
     * @throws OrderNotFoundException если заказ не найден
     * @see OrderService#getOrderById(Long)
     */
    @GetMapping("/{orderId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GetOrderById getOrderById(@PathVariable Long orderId) {
        log.info("GET запрос на получение заказа по orderId = {}", orderId);
        return orderService.getOrderById(orderId);
    }

    /**
     * Получает список всех заказов текущего аутентифицированного пользователя.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/orders/me}
     * <p>
     * <strong>Параметры запроса:</strong>
     * <table border="1" cellpadding="5" cellspacing="0">
     *     <tr>
     *         <th>Параметр</th>
     *         <th>Расположение</th>
     *         <th>Обязательный</th>
     *         <th>Описание</th>
     *     </tr>
     *     <tr>
     *         <td>—</td>
     *         <td>—</td>
     *         <td>—</td>
     *         <td>Параметры не требуются; пользователь определяется из контекста аутентификации</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — запрос успешен, тело ответа содержит список {@code List<GetOrderById>};</li>
     *     <li>{@code 401 Unauthorized} — пользователь не аутентифицирован;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/orders/me HTTP/1.1
     * Host: api.bookservice.dev
     * Accept: application/json
     * Authorization: Basic dXNlcjpwYXNzd29yZA==
     * </pre>
     * <p>
     * <strong>Пример успешного ответа:</strong>
     * <pre>
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * [
     *   {
     *     "order_id": 789,
     *     "order_number": 100500,
     *     "status": "IN_PROGRESS",
     *     "total_price": 8300.00,
     *     "created_at": "2026-05-28T10:30:00",
     *     "order_items": [...]
     *   },
     *   {
     *     "order_id": 790,
     *     "order_number": 100501,
     *     "status": "DONE",
     *     "total_price": 4500.00,
     *     "created_at": "2026-05-20T14:15:00",
     *     "order_items": [...]
     *   }
     * ]
     * </pre>
     *
     * @return список DTO {@link GetOrderById} с информацией о заказах текущего пользователя
     * @throws org.springframework.security.core.AuthenticationException если пользователь не аутентифицирован
     * @see OrderService#findAllOrdersByUser()
     * @see GetMapping
     */
    @GetMapping("/me")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetOrderById> getOrdersByUser() {
        log.info("GET запрос на получение заказа для пользователя");
        return orderService.findAllOrdersByUser();
    }

    /**
     * Обрабатывает POST-запрос на создание нового заказа.
     * <p>
     * <strong>Endpoint:</strong> {@code POST /api/v1/orders/create}
     * <p>
     * <strong>Параметры запроса:</strong>
     * <table border="1" cellpadding="5" cellspacing="0">
     *     <tr>
     *         <th>Параметр</th>
     *         <th>Расположение</th>
     *         <th>Обязательный</th>
     *         <th>Описание</th>
     *     </tr>
     *     <tr>
     *         <td>{@code postOrder}</td>
     *         <td>Request Body</td>
     *         <td>Да</td>
     *         <td>JSON-объект, содержащий список позиций заказа (ID книг и количество)</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 201 Created} — заказ успешно создан, тело ответа содержит {@link GetOrderById} с присвоенным ID;</li>
     *     <li>{@code 400 Bad Request} — некорректные входные данные (например, отрицательное количество или отсутствующие книги);</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * POST /api/v1/orders/create HTTP/1.1
     * Host: api.bookservice.dev
     * Content-Type: application/json
     *
     * {
     *   "items": [
     *     { "bookId": 123, "quantity": 1 },
     *     { "bookId": 124, "quantity": 2 }
     *   ]
     * }
     * </pre>
     * <p>
     * <strong>Пример успешного ответа:</strong>
     * <pre>
     * HTTP/1.1 201 Created
     * Content-Type: application/json
     *
     * {
     *   "orderId": 790,
     *   "orderNumber": 100501,
     *   "status": "CREATING",
     *   "totalPrice": 12100.00,
     *   "createdAt": "2026-05-29T12:00:00",
     *   "orderItems": [...]
     * }
     * </pre>
     *
     * @param postOrder DTO, содержащий данные для создания заказа
     * @return DTO {@link GetOrderById} с данными созданного заказа
     * @throws dev.bookservice.exception.bad_request.BadRequestException если входные данные невалидны
     * @see OrderService#createOrder(PostOrder)
     * @see PostMapping
     * @see RequestBody
     */
    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GetOrderById createOrder(@RequestBody PostOrder postOrder) {
        log.info("POST запрос на создание нового заказа");
        return orderService.createOrder(postOrder);
    }
}