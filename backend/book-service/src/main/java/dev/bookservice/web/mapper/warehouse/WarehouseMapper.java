package dev.bookservice.web.mapper.warehouse;

import dev.bookservice.entity.warehouse.Warehouse;
import dev.bookservice.web.dto.warehouse.GetWarehouseById;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования сущности {@link Warehouse} в DTO.
 * <p>
 * Использует библиотеку MapStruct для генерации эффективной реализации
 * преобразований на этапе компиляции. Интегрирован со Spring через
 * {@code componentModel = "spring"}.
 * <p>
 * Основная функция:
 * <ul>
 *     <li>Преобразование данных склада в краткий ответный DTO ({@link GetWarehouseById}).</li>
 * </ul>
 *
 * @see Mapper
 */
@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    /**
     * Преобразует сущность склада в DTO {@link GetWarehouseById}.
     * <p>
     * Выполняет автоматический маппинг полей с совпадающими именами:
     * {@code warehouseId} и {@code address}.
     *
     * @param warehouse сущность склада из базы данных
     * @return DTO {@link GetWarehouseById} с информацией о складе
     */
    GetWarehouseById toGetWarehouseById(Warehouse warehouse);
}