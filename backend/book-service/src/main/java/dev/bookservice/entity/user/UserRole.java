package dev.bookservice.entity.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Перечисление ролей пользователей в системе.
 * <p>
 * Определяет уровни доступа и привилегии для различных типов учетных записей.
 * Используется Spring Security для авторизации запросов к защищенным эндпоинтам.
 */
public enum UserRole {

    /**
     * Роль администратора.
     */
    ADMIN,

    /**
     * Роль обычного пользователя.
     */
    USER;

    /**
     * Преобразует роль в формат, понятный Spring Security.
     * <p>
     * Добавляет префикс {@code ROLE_} к названию роли, что является стандартом
     * для проверки прав доступа через аннотации {@code @PreAuthorize} или конфигурацию цепочки фильтров.
     *
     * @return объект {@link SimpleGrantedAuthority} с именем роли (например, {@code ROLE_USER})
     * @see org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
     */
    public SimpleGrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}