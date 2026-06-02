package dev.bookservice.repository.warehouse;

import dev.bookservice.entity.warehouse.Warehouse;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Warehouse} в базе данных.
 * <p>
 * Предоставляет методы для поиска и управления данными о складах.
 */
public interface WarehouseRepository {

    /**
     * Ищет склад по уникальному идентификатору.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional},
     * если склад с указанным идентификатором не найден.
     *
     * @param id уникальный идентификатор склада
     * @return {@code Optional<Warehouse>} с результатом поиска
     */
    Optional<Warehouse> findById(Long id);
}