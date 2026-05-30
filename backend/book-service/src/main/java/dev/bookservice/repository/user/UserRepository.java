package dev.bookservice.repository.user;

import dev.bookservice.entity.user.User;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link User} в базе данных.
 * <p>
 * Предоставляет методы для создания, поиска и управления учетными записями пользователей.
 */
public interface UserRepository {

    /**
     * Создает нового пользователя в базе данных.
     * <p>
     * Сохраняет сущность {@link User} и возвращает сохраненный объект,
     * который может содержать сгенерированный идентификатор ({@code userId}).
     *
     * @param user сущность пользователя, содержащая email, хешированный пароль и роль
     * @return сохраненная сущность {@link User}
     */
    User createNewUser(User user);

    /**
     * Ищет пользователя по уникальному идентификатору.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional},
     * если пользователь с указанным идентификатором не найден.
     *
     * @param userId уникальный идентификатор пользователя
     * @return {@code Optional<User>} с результатом поиска
     */
    Optional<User> getUserById(Long userId);

    /**
     * Ищет пользователя по адресу электронной почты.
     * <p>
     * Используется преимущественно для аутентификации, так как email является
     * уникальным логином в системе.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional},
     * если пользователь с указанной почтой не найден.
     *
     * @param email адрес электронной почты пользователя
     * @return {@code Optional<User>} с результатом поиска
     */
    Optional<User> getUserByEmail(String email);
}