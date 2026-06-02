package dev.bookservice.service.provider;

import dev.bookservice.entity.provider.Provider;
import dev.bookservice.exception.not_found.ProviderNotFoundException;
import dev.bookservice.repository.provider.ProviderRepository;
import dev.bookservice.web.dto.provider.GetProviderById;
import dev.bookservice.web.mapper.provider.ProviderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервисный слой для управления данными поставщиков.
 * <p>
 * Инкапсулирует бизнес-логику получения информации о поставщиках,
 * координируя работу репозитория и маппера перед преобразованием
 * данных в DTO.
 *
 * @see Service
 * @see ProviderRepository
 * @see ProviderMapper
 */
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;

    /**
     * Получает детальную информацию о поставщике по его уникальному идентификатору.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает сущность {@link Provider} через {@link ProviderRepository#getProviderById(Long)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи выбрасывает {@link ProviderNotFoundException};</li>
     *     <li>Преобразует сущность в DTO через {@link ProviderMapper#toProviderById(Provider)};</li>
     *     <li>Возвращает полученный DTO.</li>
     * </ol>
     *
     * @param id уникальный идентификатор поставщика
     * @return DTO {@link GetProviderById} с полной информацией о поставщике
     * @throws ProviderNotFoundException если поставщик с указанным {@code id} не найден
     * @see ProviderRepository#getProviderById(Long)
     * @see ProviderMapper#toProviderById(Provider)
     */
    public GetProviderById getProviderById(Long id) {

        Provider provider = providerRepository.getProviderById(id)
                .orElseThrow(() -> new ProviderNotFoundException(id));

        return providerMapper.toProviderById(provider);
    }
}