package dev.bookservice.service.book;

import dev.bookservice.entity.book.Book;
import dev.bookservice.exception.not_found.BookNotFoundException;
import dev.bookservice.repository.book.BookRepository;
import dev.bookservice.service.image.ImageService;
import dev.bookservice.service.publisher.PublisherService;
import dev.bookservice.web.dto.book.GetBookById;
import dev.bookservice.web.dto.image.GetImageByBookId;
import dev.bookservice.web.dto.publisher.GetPublisherByBookId;
import dev.bookservice.web.mapper.book.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный слой для управления сущностью {@link Book}.
 * <p>
 * Инкапсулирует бизнес-логику получения детальной информации о книге,
 * включая агрегацию связанных данных (изображения, издательства).
 * Выступает фасадом для координации работы репозиториев и смежных сервисов
 * ({@link ImageService}, {@link PublisherService}) перед преобразованием
 * данных в DTO через {@link BookMapper}.
 *
 * @see Service
 * @see BookRepository
 * @see ImageService
 * @see PublisherService
 * @see BookMapper
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ImageService imageService;
    private final PublisherService publisherService;
    private final BookMapper bookMapper;

    /**
     * Получает полную информацию о книге по её уникальному идентификатору.
     * <p>
     * Метод выполняет агрегацию данных из нескольких источников:
     * <ol>
     *     <li>Запрашивает основную сущность книги через {@link BookRepository#getBookByBookId(Long)};</li>
     *     <li>Получает связанное изображение через {@link ImageService#getImageByBookId(Long)};</li>
     *     <li>Загружает список издательств, связанных с книгой, через {@link PublisherService#getPublisherByBookId(Long)};</li>
     *     <li>Преобразует объединённые данные в DTO {@link GetBookById} с помощью {@link BookMapper#toDto(Book, GetImageByBookId, List)}.</li>
     * </ol>
     * <p>
     * В случае отсутствия книги в базе данных выбрасывается {@link BookNotFoundException}.
     * Ошибки поиска изображения или издательств обрабатываются в соответствующих сервисах
     * и пробрасываются на уровень выше без дополнительной трансформации.
     *
     * @param bookId уникальный идентификатор книги
     * @return DTO {@link GetBookById}, содержащий основную информацию о книге, изображение и список издательств
     * @throws BookNotFoundException если книга с указанным {@code bookId} не найдена
     * @implNote Текущая реализация выполняет последовательные синхронные вызовы.
     * При высокой нагрузке рекомендуется рассмотреть параллельное выполнение через
     * {@link java.util.concurrent.CompletableFuture} или объединение запросов на уровне БД
     * через {@code JOIN} и маппинг результата в один запрос.
     * @see BookNotFoundException
     */
    public GetBookById getBookById(Long bookId) {
        Book book = bookRepository.getBookByBookId(bookId)
                .orElseThrow(() -> {
                            log.warn("Книга по id = {} не найдена", bookId);
                            return new BookNotFoundException("Книга по id " + bookId + " не найдена");
                        }
                );

        GetImageByBookId image = imageService.getImageByBookId(bookId);

        List<GetPublisherByBookId> publishes = publisherService.getPublisherByBookId(bookId);

        return bookMapper.toDto(book, image, publishes);
    }
}

