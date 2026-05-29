package dev.bookservice.web.dto.order;

import dev.bookservice.web.dto.order_item.PostOrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO для создания нового заказа.
 * <p>
 * Используется в теле POST-запроса при оформлении заказа. Содержит список
 * выбираемых товаров и ожидаемую итоговую сумму, которая проверяется
 * на стороне сервера для обеспечения целостности данных.
 */
@Data
public class PostOrder {

    /**
     * Ожидаемая общая сумма заказа.
     * <p>
     * Передается клиентом для валидации. Если расчетная сумма на сервере
     * не совпадет с этим значением, создание заказа будет отклонено
     * во избежание манипуляций с ценами.
     */
    private BigDecimal totalPrice;

    /**
     * Список позиций (товаров), включаемых в заказ.
     * <p>
     * Каждая позиция содержит идентификатор книги и желаемое количество.
     */
    private List<PostOrderItem> orderItems;
}
