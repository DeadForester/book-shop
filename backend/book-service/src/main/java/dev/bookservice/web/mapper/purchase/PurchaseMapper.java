package dev.bookservice.web.mapper.purchase;

import dev.bookservice.entity.purchase.Purchase;
import dev.bookservice.web.dto.book.GetBookForPurchase;
import dev.bookservice.web.dto.provider.GetProviderForPurchase;
import dev.bookservice.web.dto.purchase.CreatePurchase;
import dev.bookservice.web.dto.purchase.GetPurchaseById;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования сущности {@link Purchase} и связанных данных в DTO,
 * а также для преобразования запросов на создание покупки в сущность.
 * <p>
 * Использует библиотеку MapStruct для генерации эффективной реализации
 * преобразований на этапе компиляции. Интегрирован со Spring через
 * {@code componentModel = "spring"}.
 * <p>
 * Основные функции:
 * <ul>
 *     <li>Формирование детального ответа о покупке ({@link GetPurchaseById});</li>
 *     <li>Преобразование входящего DTO запроса ({@link CreatePurchase}) в сущность {@link Purchase}.</li>
 * </ul>
 *
 * @see Mapper
 */
@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    /**
     * Преобразует сущность покупки и связанные DTO книги и поставщика
     * в итоговый DTO {@link GetPurchaseById}.
     * <p>
     * Алгоритм маппинга:
     * <ol>
     *     <li>Копирует основные поля сущности {@link Purchase} (количество, суммы, даты);</li>
     *     <li>Вкладывает DTO {@link GetBookForPurchase} в поле {@code book};</li>
     *     <li>Вкладывает DTO {@link GetProviderForPurchase} в поле {@code provider};</li>
     *     <li>Остальные поля преобразуются автоматически на основе совпадающих имён.</li>
     * </ol>
     *
     * @param purchase сущность покупки из базы данных
     * @param book     DTO с информацией о книге
     * @param provider DTO с информацией о поставщике
     * @return собранный DTO {@link GetPurchaseById}
     */
    @Mapping(target = "book", source = "book")
    @Mapping(target = "provider", source = "provider")
    GetPurchaseById toGetPurchaseById(Purchase purchase, GetBookForPurchase book, GetProviderForPurchase provider);

    /**
     * Преобразует DTO запроса на создание покупки в сущность {@link Purchase}.
     * <p>
     * Используется перед сохранением новой записи в базу данных.
     * <p>
     * Особенности маппинга:
     * <ul>
     *     <li>Игнорирует поле {@code purchaseId} (генерируется базой данных);</li>
     *     <li>Игнорирует поле {@code createdAt} (устанавливается сервисным слоем или триггером БД);</li>
     *     <li>Игнорирует поле {@code arrivedAt} (заполняется позже при изменении статуса доставки);</li>
     *     <li>Копирует {@code bookId}, {@code providerId}, {@code quantity} и {@code totalSum} из запроса.</li>
     * </ul>
     *
     * @param request DTO {@link CreatePurchase} с данными от клиента
     * @return новая сущность {@link Purchase}, готовая к сохранению
     */
    @Mapping(target = "purchaseId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "arrivedAt", ignore = true)
    Purchase toEntity(CreatePurchase request);
}