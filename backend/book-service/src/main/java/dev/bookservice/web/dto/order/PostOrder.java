package dev.bookservice.web.dto.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.order_item.PostOrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostOrder {

    /**
     * Ожидаемая общая сумма заказа.
     */
    @NotNull(message = "Общая сумма заказа должна быть не пустой")
    @DecimalMin(value = "0.01", message = "Сумма заказа должна быть отличной нуля")
    private BigDecimal totalPrice;

    /**
     * Список позиций (товаров), включаемых в заказ.
     */
    @NotNull(message = "Список товаров не может быть пустым")
    @NotEmpty(message = "Список товаров не может быть пустым. Заказ должен содержать хотя бы 1 заказ")
    @Valid
    private List<PostOrderItem> orderItems;
}
