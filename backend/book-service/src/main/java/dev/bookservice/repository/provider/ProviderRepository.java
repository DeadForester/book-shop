package dev.bookservice.repository.provider;

import dev.bookservice.entity.provider.Provider;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Provider} в базе данных.
 * <p>
 * Предоставляет методы для поиска и управления данными о поставщиках.
 */
public interface ProviderRepository {

    /**
     * Ищет поставщика по уникальному идентификатору.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional},
     * если поставщик с указанным идентификатором не найден.
     *
     * @param id уникальный идентификатор поставщика
     * @return {@code Optional<Provider>} с результатом поиска
     */
    Optional<Provider> getProviderById(Long id);
}