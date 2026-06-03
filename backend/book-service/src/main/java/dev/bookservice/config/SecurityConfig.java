package dev.bookservice.config;

import dev.bookservice.entity.user.User;
import dev.bookservice.entity.user.UserRole;
import dev.bookservice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Конфигурация безопасности Spring Security для приложения.
 * <p>
 * Определяет правила доступа к HTTP-эндпоинтам, настраивает механизм аутентификации
 * пользователей через базу данных и параметры кодирования паролей.
 * <p>
 * Основные особенности конфигурации:
 * <ul>
 *     <li>Использование stateless-сессий (без сохранения состояния на сервере);</li>
 *     <li>Отключение CSRF-защиты (актуально для REST API с токенами или Basic Auth);</li>
 *     <li>Разграничение доступа: публичные эндпоинты для книг и регистрации, защищенные — для заказов;</li>
 *     <li>Интеграция с {@link UserRepository} для загрузки данных пользователя при входе.</li>
 * </ul>
 *
 * @see EnableWebSecurity
 * @see SecurityFilterChain
 * @see UserDetailsService
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    /**
     * Настраивает цепочку фильтров безопасности (Security Filter Chain).
     * <p>
     * Алгоритм настройки:
     * <ol>
     *     <li>Отключает защиту от CSRF-атак, так как API является stateless;</li>
     *     <li>Устанавливает политику создания сессий в {@code STATELESS} (сервер не хранит состояние клиента);</li>
     *     <li>Настраивает правила авторизации запросов:
     *         <ul>
     *             <li>{@code /api/v1/registration}, {@code /api/v1/books/**}, {@code /api/v1/publishers/id} Swagger — доступны всем ({@code permitAll});</li>
     *             <li>{@code /api/v1/orders/**}, {@code /api/v1/user/**} — доступны только пользователям с ролью {@code USER};</li>
     *             <li>Все остальные запросы требуют аутентификации ({@code authenticated}).</li>
     *         </ul>
     *     </li>
     *     <li>Включает базовую HTTP-аутентификацию (Basic Auth) с именем.realm "BookService".</li>
     * </ol>
     *
     * @param http объект конфигурации HTTP-безопасности
     * @return настроенная цепочка фильтров {@link SecurityFilterChain}
     * @throws Exception если произошла ошибка при настройке безопасности
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/registration",
                                "/api/v1/books",
                                "/api/v1/books/**",
                                "/api/v1/publishers/id",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api/v1/login"
                        ).permitAll()
                        .requestMatchers(
                                "/api/v1/orders/**",
                                "/api/v1/user/**"
                        ).hasRole(UserRole.USER.name())
                        .requestMatchers(
                                "/api/v1/purchases/**",
                                "/api/v1/providers/**",
                                "/api/v1/warehouses/**"
                        ).hasRole(UserRole.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.realmName("BookService"))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Создает бин для кодирования паролей.
     * <p>
     * Используется алгоритм {@link BCryptPasswordEncoder} с силой хеширования 14.
     * Чем выше значение strength (от 4 до 31), тем медленнее выполняется хеширование,
     * что повышает устойчивость к brute-force атакам.
     *
     * @return экземпляр {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

    /**
     * Предоставляет сервис для загрузки данных пользователя по имени (email).
     * <p>
     * Интегрируется с {@link UserRepository} для поиска пользователя в базе данных.
     * Преобразует сущность {@link User} в объект Spring Security {@link org.springframework.security.core.userdetails.User},
     * назначая ему соответствующие роли (Authorities).
     *
     * @return реализация {@link UserDetailsService}
     * @throws UsernameNotFoundException если пользователь с указанным email не найден
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.getUserByEmail(username).orElseThrow(
                    () -> new UsernameNotFoundException("Пользователь " + username + " не найден")
            );
            Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
        };
    }

    /**
     * Предоставляет менеджер аутентификации.
     * <p>
     * Необходим для явной аутентификации пользователей в контроллерах (например, в {@code AuthController})
     * через {@link AuthenticationManager#authenticate(Authentication)}.
     *
     * @param configuration конфигурация аутентификации Spring Security
     * @return экземпляр {@link AuthenticationManager}
     * @throws Exception если не удалось получить менеджер аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}