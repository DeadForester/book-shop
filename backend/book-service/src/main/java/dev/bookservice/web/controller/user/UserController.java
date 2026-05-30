package dev.bookservice.web.controller.user;

import dev.bookservice.service.user.UserService;
import dev.bookservice.web.dto.user.GetUserById;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления запросами, связанными с пользователями.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения информации о профилях пользователей.
 * Базовый путь: {@code /api/v1}.
 *
 * @see RestController
 * @see RequestMapping
 * @see UserService
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Получает информацию о пользователе по его уникальному идентификатору.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/user/{userId}}
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
     *         <td>{@code userId}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор пользователя (положительное число типа {@link Long})</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — пользователь найден, тело ответа содержит {@link GetUserById};</li>
     *     <li>{@code 404 Not Found} — пользователь с указанным идентификатором не найден;</li>
     *     <li>{@code 401 Unauthorized} — запрос не аутентифицирован (если доступ защищен);</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/user/123 HTTP/1.1
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
     *   "user_id": 123,
     *   "email": "123@mail.ru"
     * }
     * </pre>
     *
     * @param userId уникальный идентификатор запрашиваемого пользователя
     * @return DTO {@link GetUserById} с информацией о пользователе
     * @throws dev.bookservice.exception.not_found.NotFoundException если пользователь не найден
     * @see UserService#getUserById(Long)
     * @see PathVariable
     * @see GetMapping
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GetUserById getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}