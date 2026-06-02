package dev.bookservice.web.dto.purchase;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.book.GetBookForPurchase;
import dev.bookservice.web.dto.provider.GetProviderForPurchase;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для представления полной информации о покупке.
 * <p>
 * Используется в ответах API при получении детальных данных о конкретной покупке.
 * Содержит агрегированную информацию о товаре, поставщике, финансовых параметрах
 * и временных метках жизненного цикла заказа.
 * <p>
 * Благодаря аннотации {@link JsonNaming}, поля объекта будут сериализованы
 * и десериализованы в формате snake_case (например, {@code purchase_id}, {@code total_sum}).
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetPurchaseById {

    /**
     * Уникальный идентификатор покупки.
     */
    private Long purchaseId;

    /**
     * Информация о купленной книге.
     * <p>
     * Содержит идентификатор, название и имя издательства в виде DTO {@link GetBookForPurchase}.
     */
    private GetBookForPurchase book;

    /**
     * Информация о поставщике, ответственном за отгрузку.
     * <p>
     * Содержит идентификатор, название и контактный номер в виде DTO {@link GetProviderForPurchase}.
     */
    private GetProviderForPurchase provider;

    /**
     * Количество единиц товара в данной покупке.
     */
    private Long quantity;

    /**
     * Итоговая стоимость покупки.
     * <p>
     * Рассчитывается как произведение цены товара на количество. Используется для финансового отчета и отображения в чеке.
     */
    private BigDecimal totalSum;

    /**
     * Дата и время оформления покупки.
     */
    private LocalDateTime createdAt;

    /**
     * Дата и время поступления покупки (доставки или готовности к выдаче).
     * <p>
     * Может быть {@code null}, если заказ еще находится в процессе обработки или транспортировки.
     */
    private LocalDateTime arrivedAt;
}