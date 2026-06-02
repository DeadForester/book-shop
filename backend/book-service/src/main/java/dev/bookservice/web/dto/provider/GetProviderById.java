package dev.bookservice.web.dto.provider;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * DTO для представления детальной информации о поставщике (провайдере).
 * <p>
 * Используется в ответах API при получении данных о контрагенте по его идентификатору.
 * Содержит название организации, адрес и контактные данные.
 * <p>
 * Благодаря аннотации {@link JsonNaming}, поля объекта будут сериализованы
 * и десериализованы в формате snake_case (например, {@code provider_id}, {@code contact_number}).
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetProviderById {

    /**
     * Уникальный идентификатор поставщика.
     */
    private Long providerId;

    /**
     * Название поставщика или организации.
     */
    private String title;

    /**
     * Юридический или фактический адрес поставщика.
     */
    private String address;

    /**
     * Контактный номер телефона для связи с поставщиком.
     */
    private String contactNumber;
}