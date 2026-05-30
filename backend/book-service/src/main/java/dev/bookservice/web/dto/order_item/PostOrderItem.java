package dev.bookservice.web.dto.order_item;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.book.GetBookByOrderItem;
import dev.bookservice.web.dto.order.PostOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO для представления позиции в запросе на создание заказа.
 * <p>
 * Используется как часть {@link PostOrder} для передачи информации о товаре
 * и его количестве при оформлении нового заказа.
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostOrderItem {

    /**
     * Информация о книге, добавляемой в заказ.
     * <p>
     * Обычно содержит только идентификатор книги ({@code bookId}), необходимый
     * для поиска сущности на сервере.
     */
    @NotNull(message = "Книга не может быть пустой")
    @NotEmpty(message = "Заказ должен содержать хотя бы 1 книгу")
    private GetBookByOrderItem book;

    /**
     * Количество единиц товара.
     * <p>
     * Должно быть положительным целым числом.
     */
    @NotNull(message = "Количество книг не может быть пустым")
    @Min(value = 1, message = "Количество книг не может быть меньше 1")
    @Max(value = 50, message = "Нельзя заказать более 50 книг за раз")
    private Integer quantity;
}
