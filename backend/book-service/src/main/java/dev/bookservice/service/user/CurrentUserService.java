package dev.bookservice.service.user;

import dev.bookservice.entity.user.User;
import dev.bookservice.exception.not_found.UserNotFoundException;
import dev.bookservice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения данных текущего аутентифицированного пользователя.
 * <p>
 * Извлекает информацию о пользователе из контекста безопасности Spring Security
 * ({@link SecurityContextHolder}) и предоставляет удобный интерфейс для получения
 * идентификатора текущего пользователя в бизнес-логике приложения.
 *
 * @see SecurityContextHolder
 * @see UserRepository
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentUserService {

    private final UserRepository userRepository;

    /**
     * Получает уникальный идентификатор текущего аутентифицированного пользователя.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Извлекает email текущего пользователя из контекста безопасности через {@link #getCurrentUserEmail()};</li>
     *     <li>Запрашивает сущность {@link User} по email через {@link UserRepository#getUserByEmail(String)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи логирует ошибку уровня {@code ERROR} и выбрасывает {@link UsernameNotFoundException};</li>
     *     <li>Возвращает идентификатор пользователя ({@code userId}).</li>
     * </ol>
     *
     * @return уникальный идентификатор текущего пользователя ({@link Long})
     * @throws UsernameNotFoundException если пользователь не найден в базе данных по извлеченному email
     * @see #getCurrentUserEmail()
     * @see UserRepository#getUserByEmail(String)
     */
    public Long getCurrentUserId() {
        String email = getCurrentUserEmail();
        return userRepository.getUserByEmail(email).map(User::getUserId).orElseThrow(() -> {
            log.error("Пользователь с email={} не найден в БД", email);
            return new UsernameNotFoundException("Пользователь не найден");
        });
    }

    /**
     * Извлекает email текущего аутентифицированного пользователя из контекста безопасности.
     * <p>
     * Проверяет наличие активной сессии и статус аутентификации.
     *
     * @return email текущего пользователя
     * @throws UserNotFoundException если пользователь не аутентифицирован или контекст безопасности пуст
     */
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Пользователь не авторизирован");
            throw new UserNotFoundException("Пользователь не аутентифицирован");
        }

        return authentication.getName();
    }
}