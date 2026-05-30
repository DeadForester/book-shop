package dev.bookservice.entity.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Сущность пользователя системы.
 * <p>
 * Представляет запись в таблице {@code USRS} и содержит основные учетные данные
 * для аутентификации и авторизации в приложении.
 *
 * @see UserRole
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USRS")
public class User {

    /**
     * Уникальный идентификатор пользователя.
     * <p>Является первичным ключом таблицы {@code USRS}.
     */
    @Id
    private Long userId;

    /**
     * Электронная почта пользователя.
     * <p>
     * Используется как уникальный логин для входа в систему (аутентификации).
     * Должна быть уникальной в рамках всей базы данных.
     */
    private String email;

    /**
     * Пароль пользователя.
     * <p>
     * Хранится в зашифрованном виде (например, BCrypt).
     * Никогда не должен сохраняться или передаваться в открытом виде.
     */
    private String password;

    /**
     * Роль пользователя в системе.
     * <p>
     * Определяет уровень доступа к ресурсам приложения (например, {@code USER}, {@code ADMIN}).
     *
     * @see UserRole
     */
    private UserRole role;
}