package dev.bookservice.web.controller.book;

import dev.bookservice.exception.not_found.BookNotFoundException;
import dev.bookservice.service.book.BookService;
import dev.bookservice.web.dto.book.GetAllBooks;
import dev.bookservice.web.dto.book.GetBookById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления запросами, связанными с книгами.
 * <p>
 * Обрабатывает HTTP-запросы к конечным точкам API версии {@code v1}
 * для получения информации о книгах. Все эндпоинты имеют базовый путь
 * {@code /api/v1/books}.
 * <p>
 * Класс использует аннотации:
 * <ul>
 *     <li>{@link RestController} — комбинирует {@link org.springframework.web.servlet.mvc.Controller} и {@link ResponseBody},
 *     обеспечивая автоматическую сериализацию возвращаемых объектов в JSON;</li>
 *     <li>{@link RequiredArgsConstructor} — генерирует конструктор для внедрения зависимостей;</li>
 *     <li>{@link Slf4j} — предоставляет поле {@code log} для логирования;</li>
 *     <li>{@link RequestMapping} — задаёт базовый путь для всех методов контроллера.</li>
 * </ul>
 *
 * @see RestController
 * @see RequestMapping
 * @see BookService
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    /**
     * Обрабатывает GET-запрос на получение детальной информации о книге.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/books/{bookId}}
     * <p>
     * <strong>Параметры запроса:</strong>
     * <table border="1" cellpadding="5" cellspacing="0">
     *     <tr>
     *         <th>Параметр</th>
     *         <th>Расположение</th>
     *         <th>Обязательный</th>
     *         <th>Описание</th>
     *     </tr>
     *     <tr>
     *         <td>{@code bookId}</td>
     *         <td>Path Variable</td>
     *         <td>Да</td>
     *         <td>Уникальный идентификатор книги (положительное число типа {@link Long})</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — книга найдена, тело ответа содержит {@link GetBookById};</li>
     *     <li>{@code 404 Not Found} — книга с указанным идентификатором не найдена (обработка {@link dev.bookservice.exception.not_found.BookNotFoundException} через {@link org.springframework.web.bind.annotation.ControllerAdvice});</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/books/123 HTTP/1.1
     * Host: api.bookservice.dev
     * Accept: application/json
     * </pre>
     * <p>
     * <strong>Пример успешного ответа:</strong>
     * <pre>
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * {
     *   "bookId": 123,
     *   "title": "Effective Java",
     *   "genre": "Programming",
     *   "creationYear": 2018,
     *   "pages": 416,
     *   "description": "Best practices for Java developers",
     *   "binding": "HARDCOVER",
     *   "amount": 4500.00,
     *   "image": { "imageId": 456, "url": "/images/effective-java.jpg" },
     *   "publishers": [ { "publisherId": 789, "name": "Addison-Wesley" } ]
     * }
     * </pre>
     *
     * @param bookId уникальный идентификатор запрашиваемой книги
     * @return DTO {@link GetBookById} с полной информацией о книге, включая изображение и список издательств
     * @see BookService#getBookById(Long)
     * @see PathVariable
     * @see GetMapping
     */
    @GetMapping("/{bookId}")
    @ResponseStatus(code = HttpStatus.OK)
    public GetBookById getBookById(@PathVariable Long bookId) {
        log.info("GET запрос на получение книги по id={}", bookId);
        return bookService.getBookById(bookId);
    }

    /**
     * Обрабатывает GET-запрос на получение списка всех книг с поддержкой пагинации.
     * <p>
     * <strong>Endpoint:</strong> {@code GET /api/v1/books}
     * <p>
     * <strong>Параметры запроса:</strong>
     * <table border="1" cellpadding="5" cellspacing="0">
     *     <tr>
     *         <th>Параметр</th>
     *         <th>Расположение</th>
     *         <th>Обязательный</th>
     *         <th>Значение по умолчанию</th>
     *         <th>Описание</th>
     *     </tr>
     *     <tr>
     *         <td>{@code page}</td>
     *         <td>Query Param</td>
     *         <td>Нет</td>
     *         <td>{@code 0}</td>
     *         <td>Номер страницы (нумерация с нуля)</td>
     *     </tr>
     *     <tr>
     *         <td>{@code size}</td>
     *         <td>Query Param</td>
     *         <td>Нет</td>
     *         <td>{@code 10}</td>
     *         <td>Количество элементов на странице</td>
     *     </tr>
     * </table>
     * <p>
     * <strong>Возможные ответы:</strong>
     * <ul>
     *     <li>{@code 200 OK} — список книг, тело ответа содержит {@code List<GetAllBooks>};</li>
     *     <li>{@code 404 Not Found} — книги в базе данных отсутствуют (обработка {@link BookNotFoundException});</li>
     *     <li>{@code 500 Internal Server Error} — непредвиденная ошибка на стороне сервера.</li>
     * </ul>
     * <p>
     * <strong>Пример запроса:</strong>
     * <pre>
     * GET /api/v1/books?page=0&size=20 HTTP/1.1
     * Host: api.bookservice.dev
     * Accept: application/json
     * </pre>
     * <p>
     * <strong>Пример успешного ответа:</strong>
     * <pre>
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * [
     *   {
     *     "id": 123,
     *     "title": "Effective Java",
     *     "image": { "image_id": 456, "url": "/images/effective-java.jpg" },
     *     "amount": 4500.00,
     *     "genre": "Programming"
     *   },
     *   {
     *     "id": 124,
     *     "title": "Clean Code",
     *     "image": { "image_id": 457, "url": "/images/clean-code.jpg" },
     *     "amount": 3800.00,
     *     "genre": "Programming"
     *   }
     * ]
     * </pre>
     *
     * @param page номер страницы (нумерация с нуля)
     * @param size количество элементов на странице
     * @return список DTO {@link GetAllBooks} с краткой информацией о каждой книге
     * @see BookService#findAllBooks(int, int)
     * @see GetMapping
     * @see RequestParam
     */
    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetAllBooks> findAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET запрос на получение всех книг, page={}, size={}", page, size);

        return bookService.findAllBooks(page, size);
    }
}
