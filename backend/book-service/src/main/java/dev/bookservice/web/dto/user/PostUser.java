package dev.bookservice.web.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для регистрации нового пользователя или аутентификации.
 * <p>
 * Используется в теле запросов {@code POST /api/v1/registration} и {@code POST /api/v1/login}.
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostUser {

    /**
     * Электронная почта пользователя.
     * <p>
     * Используется как уникальный логин для входа в систему.
     * <ul>
     *     <li>Не может быть {@code null} или пустой строкой;</li>
     *     <li>Должен соответствовать формату email (например, user@example.com).</li>
     * </ul>
     */
    @NotEmpty(message = "Поле почта не может быть пустым")
    @NotNull(message = "Поле почта не может быть null")
    @Email(message = "Введена не валидная почта")
    private String email;

    /**
     * Пароль пользователя.
     * <p>
     * Используется для аутентификации. При регистрации пароль должен быть захеширован
     * перед сохранением в базу данных.
     * <ul>
     *     <li>Не может быть {@code null} или пустой строкой;</li>
     *     <li>Минимальная длина: 8 символов;</li>
     *     <li>Максимальная длина: 128 символов.</li>
     * </ul>
     */
    @NotNull(message = "Пароль не может быть null")
    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Миминальная длина пароля равна 6 символов")
    @Size(max = 128, message = "Максимальная длина пароля равна 128 символов")
    private String password;
}