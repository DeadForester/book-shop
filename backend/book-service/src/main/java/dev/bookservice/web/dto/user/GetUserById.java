package dev.bookservice.web.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.entity.user.UserRole;
import lombok.Data;

/**
 * DTO для представления краткой информации о пользователе.
 * <p>
 * Используется в ответах API при аутентификации или получении данных профиля.
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetUserById {

    /**
     * Уникальный идентификатор пользователя.
     */
    private Long userId;

    /**
     * Электронная почта пользователя.
     * <p>
     * Используется как основной логин для аутентификации в системе.
     */
    private String email;

    private UserRole userRole;
}