package dev.bookservice.web.controller.publisher;

import dev.bookservice.service.publisher.PublisherService;
import dev.bookservice.web.dto.publisher.GetPublisherById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления запросами, связанными с издательствами.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения информации об издательствах и их книгах.
 * Базовый путь: {@code /api/v1/publishers}.
 *
 * @see RestController
 * @see RequestMapping
 * @see PublisherService
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * Получает детальную информацию об издательстве по его уникальному идентификатору.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/publishers/{publisherId}}
     * <p>
     * <strong>Параметры запроса:</strong>
     * <table border="1" cellpadding="5" cellspacing="0">
     *     <tr>
     *         <th>Параметр</th>
     *         <th>Расположение</th>
     *         <th>Обязательный</th>
     *         <th>Значение по умолчанию</th>
     *         <th>Описание</th>
     *     </tr>
     *     <tr>
     *         <td>{@code publisherId}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>—</td>
     *         <td>Уникальный идентификатор издательства</td>
     *     </tr>
     *     <tr>
     *         <td>{@code page}</td>
     *         <td>Query Param</td>
     *         <td>Нет</td>
     *         <td>{@code 0}</td>
     *         <td>Номер страницы списка книг (нумерация с нуля)</td>
     *     </tr>
     *     <tr>
     *         <td>{@code size}</td>
     *         <td>Query Param</td>
     *         <td>Нет</td>
     *         <td>{@code 10}</td>
     *         <td>Количество книг на странице</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — издательство найдено, тело ответа содержит {@link GetPublisherById};</li>
     *     <li>{@code 404 Not Found} — издательство с указанным идентификатором не найдено;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/publishers/5?page=0&size=5 HTTP/1.1
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
     *   "publisher_id": 5,
     *   "publisher_name": "Addison-Wesley",
     *   "publisher_description": "Leading publisher in computer science.",
     *   "books": [
     *     {
     *       "book_id": 123,
     *       "title": "Effective Java",
     *       "image": { ... }
     *     }
     *   ]
     * }
     * </pre>
     *
     * @param publisherId уникальный идентификатор запрашиваемого издательства
     * @param page        номер страницы для пагинации списка книг
     * @param size        количество книг на странице
     * @return DTO {@link GetPublisherById} с информацией об издательстве и списком книг
     * @throws dev.bookservice.exception.not_found.NotFoundException если издательство не найдено
     * @see PublisherService#getPublisherById(Long, int, int)
     * @see PathVariable
     * @see RequestParam
     */
    @GetMapping("/{publisherId}")
    public GetPublisherById getPublisherById(@PathVariable Long publisherId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        log.info("GET запрос на получение издательства по id = {}", publisherId);
        return publisherService.getPublisherById(publisherId, size, page);
    }
}