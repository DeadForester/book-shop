package dev.bookservice.web.controller.order;

import dev.bookservice.exception.not_found.OrderNotFoundException;
import dev.bookservice.service.order.OrderService;
import dev.bookservice.web.dto.order.GetOrderById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления запросами, связанными с заказами.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения информации о заказах. Все эндпоинты имеют базовый путь
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
     *     <li>{@code 404 Not Found} — заказ с указанным идентификатором не найден (обработка {@link OrderNotFoundException} через {@link org.springframework.web.bind.annotation.ControllerAdvice});</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/orders/789 HTTP/1.1
     * Host: api.bookservice.dev
     * Accept: application/json
     * </pre>
     * <p>
     * <strong>Пример успешного ответа:</strong>
     * <pre>
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * {
     *   "orderId": 789,
     *   "orderNumber": 100500,
     *   "status": "IN_PROGRESS",
     *   "totalPrice": 8300.00,
     *   "createdAt": "2026-05-28T10:30:00",
     *   "items": [
     *     {
     *       "orderItemId": 1,
     *       "quantity": 1,
     *       "book": {
     *         "bookId": 123,
     *         "title": "Effective Java",
     *         "priceAtPurchase": 4500.00
     *       }
     *     },
     *     {
     *       "orderItemId": 2,
     *       "quantity": 1,
     *       "book": {
     *         "bookId": 124,
     *         "title": "Clean Code",
     *         "priceAtPurchase": 3800.00
     *       }
     *     }
     *   ]
     * }
     * </pre>
     *
     * @param orderId уникальный идентификатор запрашиваемого заказа
     * @return DTO {@link GetOrderById} с полной информацией о заказе и списком позиций
     * @throws OrderNotFoundException если заказ с указанным {@code orderId} не найден
     * @see OrderService#getOrderById(Long)
     * @see PathVariable
     * @see GetMapping
     */
    @GetMapping("/{orderId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GetOrderById getOrderById(@PathVariable Long orderId) {
        log.info("GET запрос на получение заказа по orderId = {}", orderId);
        return orderService.getOrderById(orderId);
    }
}
