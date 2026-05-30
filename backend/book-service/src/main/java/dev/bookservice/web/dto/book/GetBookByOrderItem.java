package dev.bookservice.web.dto.book;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO для представления краткой информации о книге в контексте позиции заказа.
 * <p>
 * Используется при получении деталей конкретной строки заказа, где требуется
 * отобразить название товара и его стоимость на момент покупки, без загрузки
 * избыточных данных (например, описания, авторов или издательств).
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetBookByOrderItem {

    /**
     * Уникальный идентификатор книги.
     */
    private Long bookId;

    /**
     * Название книги.
     */
    private String title;

    /**
     * Стоимость книги на момент оформления заказа.
     */
    private BigDecimal amount;
}
