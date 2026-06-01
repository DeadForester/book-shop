package dev.bookservice.service.publisher;

import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.exception.bad_request.BadRequestException;
import dev.bookservice.exception.not_found.PublisherNotFoundException;
import dev.bookservice.repository.publisher.PublisherRepository;
import dev.bookservice.service.book.BookService;
import dev.bookservice.web.dto.book.GetBooksByPublisherId;
import dev.bookservice.web.dto.publisher.GetPublisherById;
import dev.bookservice.web.dto.publisher.GetPublishersByBookId;
import dev.bookservice.web.mapper.publisher.PublisherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@Slf4j
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final BookService bookService;

    @Autowired
    public PublisherService(@Lazy BookService bookService, PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.bookService = bookService;
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    /**
     * Получает издательство, связанное с указанной книгой.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Запрашивает сущность {@link Publisher} через {@link PublisherRepository#getPublisherByBookId(Long)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи логирует предупреждение уровня {@code WARN} и выбрасывает {@link PublisherNotFoundException};</li>
     *     <li>При наличии данных преобразует сущность в DTO через {@link PublisherMapper#toPublisherByBookId(Publisher)};</li>
     *     <li>Возвращает полученный DTO.</li>
     * </ol>
     *
     * @param bookId уникальный идентификатор книги, для которой требуется получить издательство
     * @return DTO {@link GetPublishersByBookId}, содержащий информацию об издательстве
     * @throws PublisherNotFoundException если для указанного {@code bookId} не найдено издательство
     * @see PublisherRepository#getPublisherByBookId(Long)
     * @see PublisherMapper#toPublisherByBookId(Publisher)
     */
    public GetPublishersByBookId getPublisherByBookId(Long bookId) {
        return publisherMapper.toPublisherByBookId(
                this.getPublisherFromDbByBookId(bookId)
        );
    }

    /**
     * Получает детальную информацию об издательстве с поддержкой пагинации списка книг.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Проверяет параметры пагинации на валидность ({@code page >= 0}, {@code size >= 1}); при ошибке выбрасывает {@link BadRequestException};</li>
     *     <li>Вычисляет смещение ({@code offset}) для запроса к базе данных;</li>
     *     <li>Запрашивает сущность {@link Publisher} через вспомогательный метод {@link #getPublisherFromDbByPublisherId(Long)};</li>
     *     <li>Загружает список книг данного издательства через {@link BookService#getAllBookByPublisherId(Long, int, int)};</li>
     *     <li>Преобразует сущность издательства и список книг в итоговый DTO через {@link PublisherMapper#toPublisherById(Publisher, List)};</li>
     *     <li>Возвращает полученный DTO.</li>
     * </ol>
     *
     * @param publisherId уникальный идентификатор издательства
     * @param size        количество книг на странице
     * @param page        номер страницы (нумерация с нуля)
     * @return DTO {@link GetPublisherById}, содержащий информацию об издательстве и список его книг
     * @throws BadRequestException       если параметры пагинации некорректны
     * @throws PublisherNotFoundException если издательство с указанным {@code publisherId} не найдено
     * @see #getPublisherFromDbByPublisherId(Long)
     * @see BookService#getAllBookByPublisherId(Long, int, int)
     * @see PublisherMapper#toPublisherById(Publisher, List)
     */
    public GetPublisherById getPublisherById(Long publisherId, int size, int page) {

        if (page < 0 || size < 1) {
            throw new BadRequestException("Укажите верный параметр");
        }

        int offset = page * size;

        Publisher publisher = getPublisherFromDbByPublisherId(publisherId);

        List<GetBooksByPublisherId> books = bookService.getAllBookByPublisherId(publisherId, offset, size);

        return publisherMapper.toPublisherById(publisher, books);
    }

    /**
     * Получает сущность {@link Publisher} по идентификатору книги.
     * <p>
     * Вспомогательный метод, инкапсулирующий логику поиска издательства, связанного с конкретной книгой,
     * и генерации исключения при неудаче.
     *
     * @param bookId уникальный идентификатор книги
     * @return найденная сущность {@link Publisher}
     * @throws PublisherNotFoundException если для указанной книги не найдено ни одного издательства
     */
    private Publisher getPublisherFromDbByBookId(Long bookId) {
        return publisherRepository.getPublisherByBookId(bookId).orElseThrow(
                () -> {
                    log.warn("Издатели по книге = {} не найдены", bookId);
                    return new PublisherNotFoundException("Издатели по книге " + bookId + " не найдены");
                }
        );
    }

    /**
     * Получает сущность {@link Publisher} по её уникальному идентификатору.
     * <p>
     * Вспомогательный метод, инкапсулирующий логику поиска издательства в репозитории
     * и генерации исключения при отсутствии записи.
     *
     * @param publisherId уникальный идентификатор издательства
     * @return найденная сущность {@link Publisher}
     * @throws PublisherNotFoundException если издательство не найдено в базе данных
     */
    private Publisher getPublisherFromDbByPublisherId(Long publisherId) {
        return publisherRepository.getPublisherById(publisherId).orElseThrow(
                () -> {
                    log.warn("Издатель по айди = {} не найден", publisherId);
                    return new PublisherNotFoundException("Издатель не найдены");
                }
        );
    }
}
