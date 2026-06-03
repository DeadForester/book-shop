package dev.bookservice.config;

import com.clickhouse.jdbc.ClickHouseDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Конфигурация подключения к ClickHouse для аналитики заказов.
 * <p>
 * Используется для ETL-процессов и выполнения аналитических запросов.
 * Отделена от основного DataSource приложения для изоляции нагрузки.
 *
 * @see JdbcTemplate
 */
@Configuration
public class ClickHouseConfig {
    @Value("${app.clickhouse.url}")
    private String url;

    @Value("${app.clickhouse.user}")
    private String user;

    @Value("${app.clickhouse.password}")
    private String password;

    /**
     * Создаёт DataSource для подключения к ClickHouse.
     *
     * @return настроенный {@link DataSource} для ClickHouse
     * @throws SQLException если не удалось установить соединение
     */
    @Bean(name = "clickHouseDataSource")
    public DataSource clickHouseDataSource() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        return new ClickHouseDataSource(url, properties);
    }

    /**
     * Создаёт JdbcTemplate для выполнения аналитических запросов в ClickHouse.
     *
     * @param clickHouseDataSource источник данных ClickHouse
     * @return {@link JdbcTemplate} для работы с ClickHouse
     */
    @Bean(name = "clickHouseJdbcTemplate")
    public JdbcTemplate clickHouseJdbcTemplate(DataSource clickHouseDataSource) {
        return new JdbcTemplate(clickHouseDataSource);
    }
}