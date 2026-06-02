package dev.bookservice.web.controller.purchase;

import dev.bookservice.service.purchase.PurchaseService;
import dev.bookservice.web.dto.purchase.CreatePurchase;
import dev.bookservice.web.dto.purchase.GetPurchaseById;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления запросами, связанными с покупками.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения информации о покупках и создания новых транзакций.
 * Все эндпоинты имеют базовый путь {@code /api/v1/purchases}.
 *
 * @see RestController
 * @see RequestMapping
 * @see PurchaseService
 */
@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    /**
     * Получает детальную информацию о покупке по её уникальному идентификатору.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/purchases/{id}}
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
     *         <td>{@code id}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор покупки (положительное число типа {@link Long})</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — покупка найдена, тело ответа содержит {@link GetPurchaseById};</li>
     *     <li>{@code 404 Not Found} — покупка с указанным идентификатором не найдена;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/purchases/123 HTTP/1.1
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
     *   "purchase_id": 123,
     *   "book": {
     *     "id": 456,
     *     "title": "Effective Java",
     *     "publisher_name": "Addison-Wesley"
     *   },
     *   "provider": {
     *     "provider_id": 789,
     *     "title": "Book Distributor LLC",
     *     "contact_number": "+7 (999) 123-45-67"
     *   },
     *   "quantity": 2,
     *   "total_sum": 9000.00,
     *   "created_at": "2026-06-01T10:00:00",
     *   "arrived_at": null
     * }
     * </pre>
     *
     * @param id уникальный идентификатор запрашиваемой покупки
     * @return DTO {@link GetPurchaseById} с полной информацией о покупке
     * @throws dev.bookservice.exception.not_found.NotFoundException если покупка не найдена
     * @see PurchaseService#getById(Long)
     * @see PathVariable
     * @see GetMapping
     */
    @GetMapping("/{id}")
    public GetPurchaseById getById(@PathVariable Long id) {
        return purchaseService.getById(id);
    }

    /**
     * Создает новую покупку на основе предоставленных данных.
     * <p>
     * <strong>Endpoint:</strong> {@code POST /api/v1/purchases/create}
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
     *         <td>{@code request}</td>
     *         <td>Request Body</td>
     *         <td>Да</td>
     *         <td>JSON-объект, содержащий ID книги, ID поставщика, количество и ожидаемую сумму</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 201 Created} — покупка успешно создана, тело ответа содержит {@link GetPurchaseById};</li>
     *     <li>{@code 400 Bad Request} — некорректные входные данные (нарушены ограничения валидации {@link CreatePurchase});</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * POST /api/v1/purchases/create HTTP/1.1
     * Host: api.bookservice.dev
     * Content-Type: application/json
     *
     * {
     *   "book_id": 456,
     *   "provider_id": 789,
     *   "quantity": 2,
     *   "total_sum": 9000.00
     * }
     * </pre>
     *
     * @param request DTO {@link CreatePurchase} с данными для оформления покупки
     * @return DTO {@link GetPurchaseById} с данными созданной покупки
     * @see PurchaseService#create(CreatePurchase)
     * @see PostMapping
     * @see RequestBody
     * @see Valid
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPurchaseById create(@Valid @RequestBody CreatePurchase request) {
        return purchaseService.create(request);
    }


    /**
     * Добавляет новую поставку (пополнение склада) на основе существующей покупки.
     * <p>
     * <strong>Endpoint:</strong> {@code POST /api/v1/purchases/add_supply}
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
     *         <td>{@code id}</td>
     *         <td>Query Param</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор покупки, на основе которой создаётся поставка</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 201 Created} — поставка успешно добавлена, тело ответа содержит {@link GetPurchaseById};</li>
     *     <li>{@code 400 Bad Request} — некорректный или отсутствующий параметр {@code id};</li>
     *     <li>{@code 404 Not Found} — покупка с указанным идентификатором не найдена;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * POST /api/v1/purchases/add_supply?id=123 HTTP/1.1
     * Host: api.bookservice.dev
     * Content-Type: application/json
     * </pre>
     * <p>
     * <strong>Пример успешного ответа:</strong>
     * <pre>
     * HTTP/1.1 201 Created
     * Content-Type: application/json
     *
     * {
     *   "purchase_id": 124,
     *   "book": {
     *     "id": 456,
     *     "title": "Effective Java",
     *     "publisher_name": "Addison-Wesley"
     *   },
     *   "provider": {
     *     "provider_id": 789,
     *     "title": "Book Distributor LLC",
     *     "contact_number": "+7 (999) 123-45-67"
     *   },
     *   "quantity": 2,
     *   "total_sum": 9000.00,
     *   "created_at": "2026-06-02T14:30:00",
     *   "arrived_at": "2026-06-02T14:30:00"
     * }
     * </pre>
     *
     * @param id уникальный идентификатор покупки, используемой как шаблон для поставки
     * @return DTO {@link GetPurchaseById} с данными созданной записи поставки
     * @throws dev.bookservice.exception.not_found.NotFoundException если покупка не найдена
     * @see PurchaseService#addNewSupply(Long)
     * @see PostMapping
     * @see RequestParam
     */
    @PostMapping("/add_supply")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPurchaseById addNewSupply(@RequestParam Long id) {
        return purchaseService.addNewSupply(id);
    }
}