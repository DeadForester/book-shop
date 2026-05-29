package dev.bookservice.web.dto.order_item;

import dev.bookservice.web.dto.book.GetBookByOrderItem;
import dev.bookservice.web.dto.order.PostOrder;
import lombok.Data;

/**
 * DTO для представления позиции в запросе на создание заказа.
 * <p>
 * Используется как часть {@link PostOrder} для передачи информации о товаре
 * и его количестве при оформлении нового заказа.
 */
@Data
public class PostOrderItem {

    /**
     * Информация о книге, добавляемой в заказ.
     * <p>
     * Обычно содержит только идентификатор книги ({@code bookId}), необходимый
     * для поиска сущности на сервере.
     */
    private GetBookByOrderItem book;

    /**
     * Количество единиц товара.
     * <p>
     * Должно быть положительным целым числом.
     */
    private Integer quantity;
}
