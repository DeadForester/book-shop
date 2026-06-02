package dev.bookservice.web.mapper.warehouse;

import dev.bookservice.entity.warehouse.WarehouseBook;
import dev.bookservice.web.dto.book.GetBookForPurchase;
import dev.bookservice.web.dto.warehouse.GetWarehouseBookInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования сущности складских запасов {@link WarehouseBook} в DTO.
 * <p>
 * Использует библиотеку MapStruct для генерации эффективной реализации
 * преобразований на этапе компиляции. Интегрирован со Spring через
 * {@code componentModel = "spring"}.
 * <p>
 * Основная функция:
 * <ul>
 *     <li>Формирование DTO с информацией о наличии книги на складе ({@link GetWarehouseBookInfo}).</li>
 * </ul>
 *
 * @see Mapper
 */
@Mapper(componentModel = "spring")
public interface WarehouseBookMapper {

    /**
     * Преобразует сущность складской записи и DTO книги в итоговый DTO {@link GetWarehouseBookInfo}.
     * <p>
     * Алгоритм маппинга:
     * <ol>
     *     <li>Копирует идентификатор склада ({@code warehouseId}) и количество ({@code quantity}) из сущности {@link WarehouseBook};</li>
     *     <li>Вкладывает переданное DTO {@link GetBookForPurchase} в поле {@code book};</li>
     *     <li>Остальные поля преобразуются автоматически на основе совпадающих имён.</li>
     * </ol>
     *
     * @param warehouseBook сущность складской записи, содержащая ID склада и количество
     * @param book          DTO с информацией о книге (название, издательство)
     * @return собранный DTO {@link GetWarehouseBookInfo}
     */
    @Mapping(target = "book", source = "book")
    GetWarehouseBookInfo toGetWarehouseBookInfo(WarehouseBook warehouseBook, GetBookForPurchase book);


}