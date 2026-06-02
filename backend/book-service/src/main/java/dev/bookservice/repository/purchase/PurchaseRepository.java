package dev.bookservice.repository.purchase;

import dev.bookservice.entity.purchase.Purchase;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Purchase} в базе данных.
 * <p>
 * Предоставляет методы для создания, поиска и обновления транзакций покупок,
 * включая фиксацию времени доставки заказа.
 */
public interface PurchaseRepository {

    /**
     * Сохраняет новую или обновляет существующую покупку в базе данных.
     * <p>
     * Возвращает сохранённую сущность, которая может содержать сгенерированные
     * системой значения (например, {@code purchaseId} при вставке).
     *
     * @param purchase сущность покупки, содержащая данные о товаре, поставщике и сумме
     * @return сохранённая сущность {@link Purchase}
     */
    Purchase save(Purchase purchase);

    /**
     * Ищет покупку по уникальному идентификатору.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional},
     * если покупка с указанным идентификатором не найдена.
     *
     * @param id уникальный идентификатор покупки
     * @return {@code Optional<Purchase>} с результатом поиска
     */
    Optional<Purchase> findById(Long id);

    /**
     * Обновляет дату и время прибытия (доставки) для указанной покупки.
     * <p>
     * Выполняет частичное обновление записи в БД, фиксируя фактический момент
     * получения товара. Используется при изменении статуса заказа на "доставлен".
     *
     * @param id  уникальный идентификатор покупки
     * @param now текущее время доставки ({@link LocalDateTime})
     * @return обновлённая сущность {@link Purchase}
     */
    Purchase saveArrivedTime(Long id, LocalDateTime now);
}