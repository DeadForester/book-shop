package dev.bookservice.entity.order;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сущность заказа в системе.
 * <p>
 * Представляет запись в таблице {@code ORDERS} и содержит основную информацию
 * о заказе: уникальный идентификатор, номер, статус, итоговую стоимость, а также
 * даты создания и последнего изменения.
 *
 * @see Status
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ORDERS")
public class Order {
    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    private Long orderId;

    /**
     * Номер заказа.
     */
    private Long orderNumber;

    /**
     * Текущий статус заказа.
     *
     * @see Status
     */
    private Status status;

    /**
     * Итоговая стоимость заказа.
     */
    private BigDecimal totalPrice;

    /**
     * Дата и время создания заказа.
     */
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего изменения заказа.
     */
    private LocalDateTime modifiedAt;
}
