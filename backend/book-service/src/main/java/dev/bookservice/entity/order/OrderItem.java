package dev.bookservice.entity.order;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Сущность позиции заказа (строки чека).
 * Представляет запись в таблице {@code ORDER_ITEMS} и содержит информацию
 * о количестве товаров в конкретной позиции заказа, а также даты создания
 * и последнего изменения записи.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ORDER_ITEMS")
public class OrderItem {
    /**
     * Уникальный идентификатор позиции заказа.
     */
    @Id
    private Long orderItemId;

    /**
     * Количество единиц товара в данной позиции.
     */
    private Integer quantity;

    /**
     * Дата и время создания записи о позиции.
     */
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего изменения записи.
     */
    private LocalDateTime modifiedAt;
}
