package dev.bookservice.service.publisher;

import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.exception.not_found.PublisherNotFoundException;
import dev.bookservice.repository.publisher.PublisherRepository;
import dev.bookservice.web.dto.publisher.GetPublisherByBookId;
import dev.bookservice.web.mapper.publisher.PublisherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * Получает список издательств, связанных с указанной книгой.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает список сущностей {@link Publisher} через {@link PublisherRepository#getPublisherByBookId(Long)};</li>
     *     <li>Проверяет результат на пустоту;</li>
     *     <li>При отсутствии записей логирует ошибку уровня {@code ERROR} и выбрасывает {@link PublisherNotFoundException};</li>
     *     <li>При наличии данных выполняет параллельное преобразование сущностей в DTO через {@link PublisherMapper#toDto(Publisher)};</li>
     *     <li>Возвращает результат в виде неизменяемого списка.</li>
     * </ol>
     *
     * @param bookId уникальный идентификатор книги, для которой требуется получить издательства
     * @return список DTO {@link GetPublisherByBookId}, содержащих информацию об издательствах
     * @throws PublisherNotFoundException если для указанного {@code bookId} не найдено ни одного издательства
     * @see PublisherRepository#getPublisherByBookId(Long)
     * @see PublisherMapper#toDto(Publisher)
     */
    public List<GetPublisherByBookId> getPublisherByBookId(Long bookId) {
        List<Publisher> publisherByBookId = publisherRepository.getPublisherByBookId(bookId);
        if (publisherByBookId.isEmpty()) {
            log.warn("Издатели по книге = {} не найдены", bookId);
            throw new PublisherNotFoundException("Издатели по книге " + bookId + " не найдены");
        }

        return publisherByBookId.stream()
                .map(publisherMapper::toDto)
                .toList();
    }
}
