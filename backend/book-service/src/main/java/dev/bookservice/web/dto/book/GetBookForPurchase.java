package dev.bookservice.web.dto.book;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * DTO для представления информации о книге в контексте покупки.
 * <p>
 * Содержит минимально необходимый набор данных для отображения товара в корзине,
 * чеке или на этапе подтверждения оформления заказа.
 * <p>
 * Благодаря аннотации {@link JsonNaming}, поля объекта будут сериализованы
 * и десериализованы в формате snake_case (например, {@code publisher_name}).
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetBookForPurchase {

    /**
     * Уникальный идентификатор книги.
     * <p>
     * Используется для внутренней обработки заказа и связи с другими сущностями системы.
     */
    private Long id;

    /**
     * Название книги.
     * <p>
     * Отображается пользователю при просмотре деталей покупки или в истории заказов.
     */
    private String title;

    /**
     * Название издательства, выпустившего книгу.
     * <p>
     * Предоставляет дополнительную контекстную информацию о товаре без загрузки
     * полной сущности издательства.
     */
    private String publisherName;
}