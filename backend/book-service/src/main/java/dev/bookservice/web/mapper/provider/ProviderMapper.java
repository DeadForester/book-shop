package dev.bookservice.web.mapper.provider;

import dev.bookservice.entity.provider.Provider;
import dev.bookservice.web.dto.provider.GetProviderById;
import dev.bookservice.web.dto.provider.GetProviderForPurchase;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования сущности {@link Provider} в DTO.
 * <p>
 * Использует библиотеку MapStruct для генерации эффективной реализации
 * преобразований на этапе компиляции. Интегрирован со Spring через
 * {@code componentModel = "spring"}.
 * <p>
 * Основные функции:
 * <ul>
 *     <li>Формирование DTO для контекста покупки ({@link GetProviderForPurchase});</li>
 *     <li>Формирование детального ответа о поставщике ({@link GetProviderById}).</li>
 * </ul>
 *
 * @see Mapper
 */
@Mapper(componentModel = "spring")
public interface ProviderMapper {

    /**
     * Преобразует сущность поставщика в DTO для контекста покупки.
     * <p>
     * Извлекает только необходимые поля (ID, название, контактный номер),
     * исключая избыточную информацию (например, адрес), которая не требуется
     * на этапе оформления или просмотра покупки.
     *
     * @param provider сущность поставщика из базы данных
     * @return DTO {@link GetProviderForPurchase}
     */
    GetProviderForPurchase toProviderForPurchase(Provider provider);

    /**
     * Преобразует сущность поставщика в DTO для детального ответа.
     * <p>
     * Выполняет полный маппинг всех доступных полей сущности
     * в соответствующие поля DTO ({@code providerId}, {@code title},
     * {@code address}, {@code contactNumber}).
     *
     * @param provider сущность поставщика из базы данных
     * @return DTO {@link GetProviderById}
     */
    GetProviderById toProviderById(Provider provider);
}