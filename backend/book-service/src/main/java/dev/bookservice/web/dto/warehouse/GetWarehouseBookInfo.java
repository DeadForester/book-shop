package dev.bookservice.web.dto.warehouse;

import dev.bookservice.web.dto.book.GetBookForPurchase;
import lombok.Data;

/**
 * DTO для представления информации о наличии книги на конкретном складе.
 * <p>
 * Используется в ответах API при запросе данных о складских запасах.
 * Содержит идентификатор склада, информацию о книге и текущее количество доступных единиц.
 */
@Data
public class GetWarehouseBookInfo {

    /**
     * Уникальный идентификатор склада.
     */
    private Long warehouseId;

    /**
     * Информация о книге, находящейся на складе.
     * <p>
     * Содержит идентификатор, название и данные издательства в виде DTO {@link GetBookForPurchase}.
     */
    private GetBookForPurchase book;

    /**
     * Текущее количество единиц данной книги на складе.
     * <p>
     * Отражает доступный запас товара для продажи или отгрузки.
     */
    private Long quantity;
}