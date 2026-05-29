package dev.bookservice.web.dto.book;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.author.GetAuthorsByBookId;
import dev.bookservice.web.dto.image.GetImageByBookId;
import dev.bookservice.web.dto.publisher.GetPublishersByBookId;
import lombok.Data;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления детальной информации о книге в ответах API.
 * <p>
 * Используется как тело ответа для эндпоинта {@code GET /api/v1/books/{bookId}}.
 * Содержит только те поля, которые необходимы клиенту, что обеспечивает:
 * <ul>
 *     <li>Контроль над форматом ответа и версионированием API;</li>
 *     <li>Защиту от непреднамеренной утечки внутренних полей сущности;</li>
 *     <li>Возможность независимой эволюции доменной модели и контракта API.</li>
 * </ul>
 * <p>
 * <strong>Особенности сериализации:</strong>
 * <ul>
 *     <li>Аннотация {@link JsonNaming @JsonNaming} с {@link PropertyNamingStrategies.SnakeCaseStrategy}
 *     автоматически преобразует имена полей из camelCase (Java) в snake_case (JSON).
 *     Например: {@code getPublisherByBookId} → {@code "publishers"} в JSON.</li>
 *     <li>Аннотация {@link Data @Data} из Lombok генерирует геттеры, сеттеры,
 *     {@code equals()}, {@code hashCode()} и {@code toString()}.</li>
 * </ul>
 * <p>
 * <strong>Пример JSON-ответа:</strong>
 * <pre>
 * {
 *   "id": 123,
 *   "title": "Effective Java",
 *   "genre": "Programming",
 *   "image": {
 *     "image_id": 456,
 *     "url": "/images/effective-java.jpg"
 *   },
 *   "description": "Best practices for Java developers",
 *   "pages": 416,
 *   "binding": "HARDCOVER",
 *   "publishers": [
 *     { "publisher_id": 789, "name": "Addison-Wesley" }
 *   ]
 * }
 * </pre>
 *
 * @see JsonNaming
 * @see PropertyNamingStrategies.SnakeCaseStrategy
 * @see Data
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetBookById {

    /**
     * Уникальный идентификатор книги.
     * <p>
     * В JSON-ответе сериализуется как {@code "id"}.
     * Соответствует полю {@code bookId} в сущности {@link dev.bookservice.entity.book.Book}.
     */
    private Long id;

    /**
     * Название книги.
     */
    private String title;

    /**
     * Жанр книги.
     */
    private String genre;

    /**
     * Информация об изображении книги.
     * <p>
     * В JSON-ответе сериализуется как вложенный объект {@code "image"}.
     * Содержит идентификатор и URL изображения через {@link GetImageByBookId}.
     * Может быть {@code null}, если изображение не загружено.
     *
     * @see GetImageByBookId
     */
    private GetImageByBookId image;

    /**
     * Список авторов связанных с книгой
     */
    private List<GetAuthorsByBookId> authors;

    /**
     * Описание книги (аннотация, синопсис).
     */
    private String description;

    /**
     * Количество страниц в книге.
     */
    private Short pages;

    /**
     * Тип переплёта книги в строковом представлении.
     * <p>
     * В JSON-ответе сериализуется как {@code "binding"}.
     * Содержит имя элемента перечисления {@link dev.bookservice.entity.book.Binding}
     * (например, {@code "SOFTCOVER"} или {@code "HARDCOVER"}).
     *
     * @see dev.bookservice.entity.book.Binding
     */
    private String binding;

    /**
     * Издательство книги.
     *
     * @see GetPublishersByBookId
     */
    private GetPublishersByBookId publisher;
}