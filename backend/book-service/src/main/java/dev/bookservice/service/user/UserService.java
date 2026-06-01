package dev.bookservice.service.user;

import dev.bookservice.entity.user.User;
import dev.bookservice.exception.bad_request.BadRequestException;
import dev.bookservice.exception.not_found.UserNotFoundException;
import dev.bookservice.repository.user.UserRepository;
import dev.bookservice.web.dto.user.GetUserById;
import dev.bookservice.web.dto.user.PostUser;
import dev.bookservice.web.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервисный слой для управления пользователями.
 * <p>
 * Инкапсулирует бизнес-логику регистрации, аутентификации и получения данных пользователей.
 * Отвечает за безопасное хеширование паролей и преобразование данных между DTO и сущностями.
 *
 * @see UserRepository
 * @see UserMapper
 * @see PasswordEncoder
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Регистрирует нового пользователя в системе.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Хеширует пароль из входного DTO с помощью {@link PasswordEncoder};</li>
     *     <li>Устанавливает захешированный пароль обратно в DTO;</li>
     *     <li>Преобразует DTO в сущность {@link User} через {@link UserMapper#toEntity(PostUser)} (роль устанавливается автоматически как USER);</li>
     *     <li>Сохраняет сущность в базе данных через {@link UserRepository#createNewUser(User)};</li>
     *     <li>Преобразует сохраненную сущность в ответный DTO {@link GetUserById}.</li>
     * </ol>
     *
     * @param newUser DTO с данными для регистрации (email, пароль)
     * @return DTO {@link GetUserById} с информацией о созданном пользователе
     * @see PasswordEncoder#encode(CharSequence)
     * @see UserMapper#toEntity(PostUser)
     */
    public GetUserById createNewUser(PostUser newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());

        newUser.setPassword(encodedPassword);

        User user = userMapper.toEntity(newUser);

        User savedUser = userRepository.createNewUser(user);

        return userMapper.toUserById(savedUser);
    }

    /**
     * Получает информацию о пользователе по его уникальному идентификатору.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает сущность {@link User} через {@link UserRepository#getUserById(Long)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи логирует ошибку уровня {@code ERROR} и выбрасывает {@link UserNotFoundException};</li>
     *     <li>Преобразует сущность в DTO {@link GetUserById}.</li>
     * </ol>
     *
     * @param userId уникальный идентификатор пользователя
     * @return DTO {@link GetUserById} с информацией о пользователе
     * @throws UserNotFoundException если пользователь с указанным {@code userId} не найден
     * @see UserRepository#getUserById(Long)
     * @see UserMapper#toUserById(User)
     */
    public GetUserById getUserById(Long userId) {

        if (userId == null) {
            throw new BadRequestException("Укажите верный параметр");
        }

        User user = userRepository.getUserById(userId).orElseThrow(() -> {
            log.error("Пользователь с id={} не найден", userId);
            return new UserNotFoundException("Пользователь с id = " + userId + " не найден");
        });
        return userMapper.toUserById(user);
    }

    /**
     * Получает информацию о пользователе по адресу электронной почты.
     * <p>
     * Используется преимущественно механизмами Spring Security для аутентификации.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает сущность {@link User} через {@link UserRepository#getUserByEmail(String)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи выбрасывает {@link UsernameNotFoundException} (стандартное исключение Spring Security);</li>
     *     <li>Преобразует сущность в DTO {@link GetUserById}.</li>
     * </ol>
     *
     * @param email адрес электронной почты пользователя
     * @return DTO {@link GetUserById} с информацией о пользователе
     * @throws UsernameNotFoundException если пользователь с указанной почтой не найден
     * @see UserRepository#getUserByEmail(String)
     * @see UserMapper#toUserById(User)
     */
    public GetUserById getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return userMapper.toUserById(user);
    }
}