package dev.bookservice.web.controller.warehouse;

import dev.bookservice.service.warehouse.WarehouseService;
import dev.bookservice.web.dto.warehouse.AddToWarehouseRequest;
import dev.bookservice.web.dto.warehouse.GetWarehouseBookInfo;
import dev.bookservice.web.dto.warehouse.GetWarehouseById;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления запросами, связанными со складами и запасами.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения информации о складах, проверки наличия книг и пополнения запасов.
 * Все эндпоинты имеют базовый путь {@code /api/v1/warehouses}.
 *
 * @see RestController
 * @see RequestMapping
 * @see WarehouseService
 */
@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
@Slf4j
public class WarehouseController {

    private final WarehouseService warehouseService;

    /**
     * Получает краткую информацию о складе по его уникальному идентификатору.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/warehouses/{warehouseId}}
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
     *         <td>{@code warehouseId}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор склада</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — склад найден, тело ответа содержит {@link GetWarehouseById};</li>
     *     <li>{@code 404 Not Found} — склад с указанным идентификатором не найден;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/warehouses/1 HTTP/1.1
     * Host: api.bookservice.dev
     * Accept: application/json
     * </pre>
     *
     * @param warehouseId уникальный идентификатор запрашиваемого склада
     * @return DTO {@link GetWarehouseById} с информацией о складе
     * @throws dev.bookservice.exception.not_found.NotFoundException если склад не найден
     * @see WarehouseService#getWarehouseInfo(Long)
     * @see PathVariable
     * @see GetMapping
     */
    @GetMapping("/{warehouseId}")
    public GetWarehouseById getWarehouse(@PathVariable Long warehouseId) {
        log.info("GET запрос на получение склада по id = {}", warehouseId);
        return warehouseService.getWarehouseInfo(warehouseId);
    }

    /**
     * Получает информацию о наличии конкретной книги на указанном складе.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/warehouses/{warehouseId}/books/{bookId}}
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
     *         <td>{@code warehouseId}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор склада</td>
     *     </tr>
     *     <tr>
     *         <td>{@code bookId}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор книги</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — запись о наличии найдена, тело ответа содержит {@link GetWarehouseBookInfo};</li>
     *     <li>{@code 404 Not Found} — склад или книга не найдены, либо книга отсутствует на данном складе;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/warehouses/1/books/42 HTTP/1.1
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
     *   "warehouse_id": 1,
     *   "book": {
     *     "id": 42,
     *     "title": "Clean Code",
     *     "publisher_name": "Prentice Hall"
     *   },
     *   "quantity": 15
     * }
     * </pre>
     *
     * @param warehouseId уникальный идентификатор склада
     * @param bookId      уникальный идентификатор книги
     * @return DTO {@link GetWarehouseBookInfo} с данными о наличии
     * @throws dev.bookservice.exception.not_found.NotFoundException если данные не найдены
     * @see WarehouseService#getStockInfo(Long, Long)
     * @see PathVariable
     * @see GetMapping
     */
    @GetMapping("/{warehouseId}/books/{bookId}")
    public GetWarehouseBookInfo getStock(@PathVariable Long warehouseId, @PathVariable Long bookId) {
        log.info("GET запрос на получение информация по книге = {} со склада = {}", bookId, warehouseId);
        return warehouseService.getStockInfo(warehouseId, bookId);
    }

    /**
     * Пополняет запасы книги на указанном складе.
     * <p>
     * <strong>Endpoint:</strong> {@code POST /api/v1/warehouses/stock}
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
     *         <td>JSON-объект, содержащий ID склада, ID книги и количество для добавления</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — запасы успешно обновлены, тело ответа содержит {@link GetWarehouseBookInfo};</li>
     *     <li>{@code 400 Bad Request} — некорректные входные данные (нарушены ограничения валидации {@link AddToWarehouseRequest});</li>
     *     <li>{@code 404 Not Found} — склад или книга не найдены;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * POST /api/v1/warehouses/stock HTTP/1.1
     * Host: api.bookservice.dev
     * Content-Type: application/json
     *
     * {
     *   "warehouse_id": 1,
     *   "book_id": 42,
     *   "quantity_to_add": 10
     * }
     * </pre>
     *
     * @param request DTO {@link AddToWarehouseRequest} с данными для пополнения
     * @return DTO {@link GetWarehouseBookInfo} с обновленными данными о наличии
     * @throws dev.bookservice.exception.not_found.NotFoundException если склад или книга не найдены
     * @see WarehouseService#addToWarehouse(AddToWarehouseRequest)
     * @see PostMapping
     * @see RequestBody
     * @see Valid
     */
    @PostMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    public GetWarehouseBookInfo addStock(@Valid @RequestBody AddToWarehouseRequest request) {
        log.info("POST запрос на добавления товара на склад");
        return warehouseService.addToWarehouse(request);
    }
}