package dev.bookservice.web.dto.provider;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * DTO для представления информации о поставщике в контексте покупки.
 * <p>
 * Используется при оформлении заказа для отображения ключевых контактных данных
 * поставщика, ответственного за отгрузку товара. Содержит только необходимые
 * поля без избыточной информации.
 * <p>
 * Благодаря аннотации {@link JsonNaming}, поля объекта будут сериализованы
 * и десериализованы в формате snake_case (например, {@code provider_id}, {@code contact_number}).
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetProviderForPurchase {

    /**
     * Уникальный идентификатор поставщика.
     * <p>
     * Используется для внутренней обработки заказа и связи с сущностью поставщика в БД.
     */
    private Long providerId;

    /**
     * Название поставщика или организации.
     * <p>
     * Отображается в чеке или деталях заказа для идентификации контрагента.
     */
    private String title;

    /**
     * Контактный номер телефона поставщика.
     * <p>
     * Может использоваться службой поддержки или системой уведомлений
     * для оперативной связи по вопросам доставки или комплектации заказа.
     */
    private String contactNumber;
}