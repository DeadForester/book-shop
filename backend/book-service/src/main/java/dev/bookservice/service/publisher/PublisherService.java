package dev.bookservice.service.publisher;

import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.exception.not_found.PublisherNotFoundException;
import dev.bookservice.repository.publisher.PublisherRepository;
import dev.bookservice.web.dto.publisher.GetPublishersByBookId;
import dev.bookservice.web.mapper.publisher.PublisherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Сервисный слой для работы с данными издательств ({@link Publisher}).
 * <p>
 * Инкапсулирует бизнес-логику поиска издательств, связанных с конкретной книгой,
 * а также преобразования сущностей в DTO. Выступает посредником между
 * {@link PublisherRepository} и {@link PublisherMapper}, обеспечивая
 * валидацию результатов и централизованное логирование.
 *
 * @see Service
 * @see PublisherRepository
 * @see PublisherMapper
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    /**
     * Получает издательство, связанное с указанной книгой.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает сущность {@link Publisher} через {@link PublisherRepository#getPublisherByBookId(Long)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи логирует предупреждение уровня {@code WARN} и выбрасывает {@link PublisherNotFoundException};</li>
     *     <li>При наличии данных преобразует сущность в DTO через {@link PublisherMapper#toDto(Publisher)};</li>
     *     <li>Возвращает полученный DTO.</li>
     * </ol>
     *
     * @param bookId уникальный идентификатор книги, для которой требуется получить издательство
     * @return DTO {@link GetPublishersByBookId}, содержащий информацию об издательстве
     * @throws PublisherNotFoundException если для указанного {@code bookId} не найдено издательство
     * @see PublisherRepository#getPublisherByBookId(Long)
     * @see PublisherMapper#toDto(Publisher)
     */
    public GetPublishersByBookId getPublisherByBookId(Long bookId) {
        Publisher publisherByBookId = publisherRepository.getPublisherByBookId(bookId).orElseThrow(
                () -> {
                    log.warn("Издатели по книге = {} не найдены", bookId);
                    return new PublisherNotFoundException("Издатели по книге " + bookId + " не найдены");
                }
        );

        return publisherMapper.toDto(publisherByBookId);
    }
}
