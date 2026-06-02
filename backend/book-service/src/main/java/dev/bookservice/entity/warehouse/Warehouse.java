package dev.bookservice.entity.warehouse;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Сущность склада.
 * <p>
 * Представляет запись в таблице {@code WAREHOUSES} и содержит информацию
 * о местах хранения книг (складах или пунктах выдачи).
 * <p>
 * Для автоматической генерации геттеров, сеттеров, конструкторов и билдера
 * используются аннотации Lombok.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "WAREHOUSES")
public class Warehouse {

    /**
     * Уникальный идентификатор склада.
     * <p>Является первичным ключом таблицы {@code WAREHOUSES}.
     */
    @Id
    private Long warehouseId;

    /**
     * Адрес расположения склада.
     * <p>Используется для логистики, отображения на карте и идентификации точки хранения.
     */
    private String address;
}