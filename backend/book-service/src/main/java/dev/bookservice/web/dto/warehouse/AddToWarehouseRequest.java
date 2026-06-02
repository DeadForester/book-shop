package dev.bookservice.web.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO для запроса на добавление товара на склад.
 * <p>
 * Используется в теле POST-запроса при оформлении операции пополнения запасов.
 * Содержит идентификаторы склада и книги, а также количество добавляемых единиц.
 * К полям применены правила валидации для обеспечения целостности данных.
 */
@Data
public class AddToWarehouseRequest {

    /**
     * Уникальный идентификатор склада, на который производится пополнение.
     * <p>
     * Обязательное поле, не может быть {@code null}.
     */
    @NotNull(message = "Поле warehouseId не может быть пустым")
    private Long warehouseId;

    /**
     * Уникальный идентификатор книги, которую необходимо добавить на склад.
     * <p>
     * Обязательное поле, не может быть {@code null}.
     */
    @NotNull(message = "Поле bookId не может быть пустым")
    private Long bookId;

    /**
     * Количество единиц товара для добавления.
     * <p>
     * Обязательное поле. Должно быть положительным числом (минимум {@code 1}).
     */
    @NotNull(message = "Поле quantityToAdd не может быть пустым")
    @Min(1)
    private Long quantityToAdd;
}