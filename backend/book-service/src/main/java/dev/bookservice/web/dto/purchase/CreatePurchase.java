package dev.bookservice.web.dto.purchase;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO для создания новой покупки.
 * <p>
 * Используется в теле запроса при оформлении заказа. Содержит идентификаторы
 * книги и поставщика, количество товара и ожидаемую итоговую сумму.
 * К полям применены строгие правила валидации для предотвращения некорректных данных.
 * <p>
 * Благодаря аннотации {@link JsonNaming}, поля объекта будут сериализованы
 * и десериализованы в формате snake_case (например, {@code book_id}, {@code total_sum}).
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreatePurchase {

    /**
     * Уникальный идентификатор покупаемой книги.
     * <p>
     * Обязательное поле, не может быть {@code null}.
     */
    @NotNull(message = "Поле bookId не может быть пустым")
    private Long bookId;

    /**
     * Уникальный идентификатор поставщика (издательства).
     * <p>
     * Обязательное поле, не может быть {@code null}.
     */
    @NotNull(message = "Поле providerId не может быть пустым")
    private Long providerId;

    /**
     * Количество экземпляров книги для покупки.
     * <p>
     * Обязательное поле. Допустимый диапазон: от {@code 1} до {@code 500} включительно.
     */
    @Min(value = 1L)
    @Max(value = 500L)
    @NotNull(message = "Поле quantity не может быть пустым")
    private Long quantity;

    /**
     * Итоговая сумма заказа.
     * <p>
     * Обязательное поле. Минимально допустимое значение: {@code 0.01}.
     * Используется для сверки с расчетной стоимостью на стороне сервера.
     */
    @NotNull(message = "Поле totalSum не может быть пустым")
    @DecimalMin(value = "0.01", message = "Сумма заказа должна быть отличной нуля")
    private BigDecimal totalSum;
}