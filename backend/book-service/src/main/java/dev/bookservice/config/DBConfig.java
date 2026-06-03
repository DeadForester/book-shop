package dev.bookservice.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Класс конфигурации для подключения к базе данных.
 * <p>
 * Настраивает и регистрирует в контексте Spring следующие бины:
 * <ul>
 *     <li>{@link HikariDataSource} — пул соединений с базой данных с настройками
 *     <li>{@link JdbcTemplate} — шаблон для упрощённой работы с JDBC на основе настроенного {@link DataSource}.</li>
 * </ul>
 * <p>
 * Параметры подключения (URL, имя пользователя, пароль) загружаются из свойств приложения
 * через аннотацию {@link Value @Value}:
 * <pre>
 *     spring.datasource.url
 *     spring.datasource.username
 *     spring.datasource.password
 * </pre>
 *
 * @see Configuration
 * @see Bean
 * @see HikariDataSource
 * @see JdbcTemplate
 */
@Configuration
@Slf4j
public class DBConfig {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Создаёт и настраивает бин {@link HikariDataSource} для управления пулом соединений с БД.
     * <p>
     * Метод выполняет следующие действия:
     * <ul>
     *     <li>Инициализирует экземпляр {@code HikariDataSource};</li>
     *     <li>Устанавливает параметры подключения: JDBC URL, имя пользователя и пароль;</li>
     *     <li>Настраивает параметры пула соединений:
     *         <ul>
     *             <li>{@code maximumPoolSize = 10} — максимальное количество соединений в пуле;</li>
     *             <li>{@code minimumIdle = 5} — минимальное количество простаивающих соединений.</li>
     *         </ul>
     *     </li>
     *     <li>Фиксирует в логах процесс подключения к базе данных.</li>
     * </ul>
     *
     * @return настроенный экземпляр {@link DataSource}, готовый к использованию
     * @see HikariDataSource#setJdbcUrl(String)
     * @see HikariDataSource#setUsername(String)
     * @see HikariDataSource#setPassword(String)
     * @see HikariDataSource#setMaximumPoolSize(int)
     * @see HikariDataSource#setMinimumIdle(int)
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        log.info("Подключение к БД. url={}", jdbcUrl);

        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);

        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setMinimumIdle(5);

        log.info("Успешное подключение к БД");

        return hikariDataSource;

    }

    /**
     * Создаёт и возвращает бин {@link JdbcTemplate} для работы с базой данных через JDBC.
     * <p>
     * Метод использует внедрённый {@link DataSource} (предпочтительно {@link HikariDataSource})
     * для инициализации шаблона, который предоставляет удобные методы для выполнения
     * запросов, обновлений и транзакционных операций.
     *
     * @param dataSource источник данных, используемый для создания соединений с БД
     * @return настроенный экземпляр {@link JdbcTemplate}
     * @see JdbcTemplate#JdbcTemplate(DataSource)
     * @see org.springframework.jdbc.core.RowMapper
     */
    @Bean(name = "postgresJdbcTemplate")
    @Primary
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
