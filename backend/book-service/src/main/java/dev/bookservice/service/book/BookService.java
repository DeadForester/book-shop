package dev.bookservice.service.book;

import dev.bookservice.entity.book.Book;
import dev.bookservice.exception.not_found.BookNotFoundException;
import dev.bookservice.repository.book.BookRepository;
import dev.bookservice.service.author.AuthorService;
import dev.bookservice.service.image.ImageService;
import dev.bookservice.service.publisher.PublisherService;
import dev.bookservice.web.dto.author.GetAuthorsByBookId;
import dev.bookservice.web.dto.book.GetAllBooks;
import dev.bookservice.web.dto.book.GetBookById;
import dev.bookservice.web.dto.image.GetImageByBookId;
import dev.bookservice.web.dto.publisher.GetPublishersByBookId;
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
    private final AuthorService authorService;
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
     *     <li>Загружает список издательств через {@link PublisherService#getPublisherByBookId(Long)};</li>
     *     <li>Загружает список авторов через {@link AuthorService#getAuthorsByBookId(Long)};</li>
     *     <li>Преобразует объединённые данные в DTO {@link GetBookById} с помощью {@link BookMapper#toDtoBookById}.</li>
     * </ol>
     * <p>
     * В случае отсутствия книги в базе данных выбрасывается {@link BookNotFoundException}.
     *
     * @param bookId уникальный идентификатор книги
     * @return DTO {@link GetBookById}, содержащий основную информацию, изображение, издательства и авторов
     * @throws BookNotFoundException если книга с указанным {@code bookId} не найдена
     * @implNote Текущая реализация выполняет последовательные синхронные вызовы.
     * При высокой нагрузке рекомендуется рассмотреть параллельное выполнение через
     * {@link java.util.concurrent.CompletableFuture} или объединение запросов на уровне БД.
     */
    public GetBookById getBookById(Long bookId) {
        Book book = bookRepository.getBookByBookId(bookId).orElseThrow(() -> {
            log.warn("Книга по id = {} не найдена", bookId);
            return new BookNotFoundException("Книга по id " + bookId + " не найдена");
        });

        GetImageByBookId image = getImageByBookId(bookId);

        GetPublishersByBookId publishes = publisherService.getPublisherByBookId(bookId);

        List<GetAuthorsByBookId> authors = getAuthorsByBookId(bookId);

        return bookMapper.toDtoBookById(book, image, publishes, authors);
    }

    /**
     * Возвращает список книг с поддержкой пагинации, изображениями и авторами.
     * <p>
     * Преобразует параметры {@code page}/{@code size} в {@code offset} для репозитория,
     * загружает сущности книг и агрегирует для каждой изображение и список авторов.
     *
     * @param page номер страницы (нумерация с нуля)
     * @param size количество элементов на странице
     * @return список DTO {@link GetAllBooks} для запрошенной страницы
     * @throws BookNotFoundException если для запрошенной страницы не найдено ни одной книги
     */
    public List<GetAllBooks> findAllBooks(int page, int size) {
        int offset = page * size;
        List<Book> allBooks = bookRepository.findAllBooks(offset, size);

        if (allBooks.isEmpty()) {
            log.warn("Нет книг в БД для страницы {}", page);
            throw new BookNotFoundException("Книги в БД не найдены");
        }

        return allBooks.stream().map(book -> {
            Long bookId = book.getBookId();
            GetImageByBookId imageByBookId = this.getImageByBookId(bookId);
            List<GetAuthorsByBookId> authors = getAuthorsByBookId(bookId);
            return bookMapper.toDtoAllBooks(book, imageByBookId, authors);
        }).toList();
    }

    /**
     * Получает DTO изображения для указанной книги.
     * <p>
     * Делегирует вызов в {@link ImageService}.
     *
     * @param bookId идентификатор книги
     * @return DTO {@link GetImageByBookId}
     */
    private GetImageByBookId getImageByBookId(Long bookId) {
        log.debug("Запрос на получение изображения по книге={}", bookId);

        return imageService.getImageByBookId(bookId);
    }

    /**
     * Получает список авторов для указанной книги.
     * <p>
     * Делегирует вызов в {@link AuthorService}.
     *
     * @param bookId идентификатор книги
     * @return список DTO {@link GetAuthorsByBookId}
     */
    private List<GetAuthorsByBookId> getAuthorsByBookId(Long bookId) {
        log.debug("Запрос на получение авторов по книге={}", bookId);

        return authorService.getAuthorsByBookId(bookId);
    }
}

