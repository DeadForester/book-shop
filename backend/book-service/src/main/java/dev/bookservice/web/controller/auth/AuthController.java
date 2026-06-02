package dev.bookservice.web.controller.auth;

import dev.bookservice.service.user.UserService;
import dev.bookservice.web.dto.user.GetUserById;
import dev.bookservice.web.dto.user.PostUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для управления аутентификацией и регистрацией пользователей.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1},
 * связанным с входом в систему и созданием новых учетных записей.
 * Базовый путь: {@code /api/v1}.
 *
 * @see RestController
 * @see AuthenticationManager
 * @see UserService
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    /**
     * Аутентифицирует пользователя по email и паролю.
     * <p>
     * <strong>Endpoint:</strong> {@code POST /api/v1/login}
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
     *         <td>{@code loginRequest}</td>
     *         <td>Request Body</td>
     *         <td>Да</td>
     *         <td>DTO, содержащий email и пароль пользователя</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — аутентификация успешна, возвращает информацию о пользователе;</li>
     *     <li>{@code 401 Unauthorized} — неверный email или пароль (обрабатывается Spring Security);</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * POST /api/v1/login HTTP/1.1
     * Host: api.bookservice.dev
     * Content-Type: application/json
     *
     * {
     *   "email": "user@example.com",
     *   "password": "securePassword123"
     * }
     * </pre>
     *
     * @param loginRequest DTO с учетными данными пользователя
     * @return DTO {@link GetUserById} с информацией об аутентифицированном пользователе
     * @throws org.springframework.security.core.AuthenticationException если аутентификация не удалась
     * @see AuthenticationManager#authenticate(Authentication)
     * @see UserService#getUserByEmail(String)
     */
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public GetUserById login(@RequestBody PostUser loginRequest) {
        log.info("Попытка пользователя = {} войти в систему", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        return userService.getUserByEmail(authentication.getName());
    }

    /**
     * Регистрирует нового пользователя в системе.
     * <p>
     * <strong>Endpoint:</strong> {@code POST /api/v1/registration}
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
     *         <td>{@code newUser}</td>
     *         <td>Request Body</td>
     *         <td>Да</td>
     *         <td>DTO, содержащий данные для регистрации (email, пароль, имя и т.д.)</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 201 Created} — пользователь успешно создан, возвращает его данные;</li>
     *     <li>{@code 400 Bad Request} — некорректные данные (например, email уже занят);</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * POST /api/v1/registration HTTP/1.1
     * Host: api.bookservice.dev
     * Content-Type: application/json
     *
     * {
     *   "email": "newuser@example.com",
     *   "password": "securePassword123"
     * }
     * </pre>
     *
     * @param newUser DTO с данными нового пользователя
     * @return DTO {@link GetUserById} с информацией о созданном пользователе
     * @see UserService#createNewUser(PostUser)
     */
    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GetUserById register(@RequestBody PostUser newUser) {
        log.info("Попытка пользователя = {} зарегестрироваться", newUser.getEmail());
        return userService.createNewUser(newUser);
    }
}