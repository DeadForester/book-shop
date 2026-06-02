package dev.bookservice.repository.warehouse;

import dev.bookservice.entity.warehouse.WarehouseBook;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link WarehouseBook} в базе данных.
 * <p>
 * Предоставляет методы для управления складскими запасами, включая сохранение
 * или обновление количества книг и поиск записей по складу и товару.
 */
public interface WarehouseBookRepository {

    /**
     * Сохраняет новую запись о наличии книги на складе или обновляет существующую.
     * <p>
     * Если запись с указанными {@code warehouseId} и {@code bookId} уже существует,
     * метод должен обновить количество ({@code quantity}) и время последнего изменения
     * ({@code modifiedAt}). Если записи нет — создает новую.
     * <p>
     * Время передается явно для обеспечения консистентности временных меток
     * в рамках одной транзакции сервиса.
     *
     * @param warehouseBook сущность, содержащая идентификаторы склада и книги, а также новое количество
     * @param now           текущее время для обновления поля {@code modifiedAt} (или {@code createdAt})
     */
    void save(WarehouseBook warehouseBook, LocalDateTime now);

    /**
     * Ищет запись о наличии книги на конкретном складе.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional},
     * если указанная книга отсутствует на данном складе.
     *
     * @param warehouseId уникальный идентификатор склада
     * @param bookId      уникальный идентификатор книги
     * @return {@code Optional<WarehouseBook>} с результатом поиска
     */
    Optional<WarehouseBook> findByWarehouseAndBook(Long warehouseId, Long bookId);
}