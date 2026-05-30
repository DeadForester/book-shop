package dev.bookservice.web.mapper.user;

import dev.bookservice.entity.user.User;
import dev.bookservice.web.dto.user.GetUserById;
import dev.bookservice.web.dto.user.PostUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования данных между сущностью пользователя и DTO.
 * <p>
 * Использует библиотеку MapStruct для генерации эффективной реализации
 * преобразований на этапе компиляции.
 * <p>
 * Основные функции:
 * <ul>
 *     <li>Преобразование запроса на регистрацию ({@link PostUser}) в сущность {@link User};</li>
 *     <li>Преобразование сущности {@link User} в ответный DTO ({@link GetUserById}).</li>
 * </ul>
 *
 * @see Mapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Преобразует DTO запроса регистрации в сущность пользователя.
     * <p>
     * Алгоритм маппинга:
     * <ol>
     *     <li>Копирует поля {@code email} и {@code password} из DTO в сущность;</li>
     *     <li>Явно устанавливает роль пользователя в {@link dev.bookservice.entity.user.UserRole#USER},
     *         игнорируя возможные значения роли во входных данных (безопасность);</li>
     *     <li>Остальные поля (например, {@code userId}) остаются null или заполняются БД.</li>
     * </ol>
     *
     * @param newUser DTO с данными для регистрации
     * @return сущность {@link User}, готовая к сохранению
     */
    @Mapping(target = "role", expression = "java(dev.bookservice.entity.user.UserRole.USER)")
    User toEntity(PostUser newUser);

    /**
     * Преобразует сущность пользователя в DTO для ответа.
     * <p>
     * Извлекает только необходимые для отображения данные (ID и email),
     * исключая чувствительную информацию (пароль, роль).
     *
     * @param user сущность пользователя из базы данных
     * @return DTO {@link GetUserById}
     */
    GetUserById toUserById(User user);
}