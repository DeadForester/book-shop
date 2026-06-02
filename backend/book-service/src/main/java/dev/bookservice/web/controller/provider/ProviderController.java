package dev.bookservice.web.controller.provider;

import dev.bookservice.service.provider.ProviderService;
import dev.bookservice.web.dto.provider.GetProviderById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления запросами, связанными с поставщиками.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения справочной информации о поставщиках (провайдерах).
 * Все эндпоинты имеют базовый путь {@code /api/v1/providers}.
 *
 * @version 1.0
 * @see RestController
 * @see RequestMapping
 * @see ProviderService
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/providers")
@Slf4j
public class ProviderController {

    private final ProviderService providerService;

    /**
     * Получает детальную информацию о поставщике по его уникальному идентификатору.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/providers/{id}}
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
     *         <td>Уникальный идентификатор поставщика (положительное число типа {@link Long})</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — поставщик найден, тело ответа содержит {@link GetProviderById};</li>
     *     <li>{@code 404 Not Found} — поставщик с указанным идентификатором не найден;</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/providers/42 HTTP/1.1
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
     *   "provider_id": 42,
     *   "title": "Книжный Мир Лтд.",
     *   "address": "г. Москва, ул. Примерная, д. 10",
     *   "contact_number": "+7 (999) 123-45-67"
     * }
     * </pre>
     *
     * @param id уникальный идентификатор запрашиваемого поставщика
     * @return DTO {@link GetProviderById} с информацией о поставщике
     * @throws dev.bookservice.exception.not_found.NotFoundException если поставщик не найден
     * @see ProviderService#getProviderById(Long)
     * @see PathVariable
     * @see GetMapping
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetProviderById getProviderById(@PathVariable Long id) {
        log.info("GET запрос на получение поставщика по id = {}", id);
        return providerService.getProviderById(id);
    }
}