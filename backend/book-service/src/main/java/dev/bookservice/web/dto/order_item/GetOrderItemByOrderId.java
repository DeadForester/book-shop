package dev.bookservice.web.dto.order_item;

import dev.bookservice.web.dto.book.GetBookByOrderItem;
import lombok.Data;

/**
 * DTO для представления позиции заказа (строки чека).
 */
@Data
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
