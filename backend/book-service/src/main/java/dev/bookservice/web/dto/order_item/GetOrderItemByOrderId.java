package dev.bookservice.web.dto.order_item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.book.GetBookByOrderItem;
import lombok.Data;

/**
 * DTO для представления позиции заказа (строки чека).
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetOrderItemByOrderId {

    /**
     * Уникальный идентификатор позиции заказа.
     */
    private Long orderItemId;

    /**
     * Информация о книге в данной позиции.
     */
    private GetBookByOrderItem book;

    /**
     * Количество единиц товара в данной позиции.
     */
    private Long quantity;
}
