package dev.bookservice.web.dto.order;

import dev.bookservice.web.dto.order_item.GetOrderItemByOrderId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO для представления полной информации о заказе.
 */
@Data
public class GetOrderById {

    /**
     * Уникальный технический идентификатор заказа (Primary Key).
     */
    private Long orderId;

    /**
     * Публичный номер заказа.
     * <p>
     * Используется для отображения пользователю и в коммуникации (например, "Ваш заказ №100500").
     */
    private String orderNumber;

    /**
     * Список позиций (товаров) в заказе.
     */
    private List<GetOrderItemByOrderId> orderItems;

    /**
     * Текущий статус заказа.
     * <p>
     * Возможные значения: {@code CREATING}, {@code PROCESSING}, {@code IN_PROGRESS}, {@code CANCELED}, {@code DONE}.
     */
    private String status;

    /**
     * Итоговая сумма заказа.
     */
    private BigDecimal totalPrice;

    /**
     * Дата и время создания заказа.
     */
    private LocalDateTime createdAt;
}