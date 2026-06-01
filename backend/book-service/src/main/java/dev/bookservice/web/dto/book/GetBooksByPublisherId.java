package dev.bookservice.web.dto.book;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.entity.image.Image;
import lombok.Data;

/**
 * DTO для представления краткой информации о книге в контексте издательства.
 * <p>
 * Используется при получении списка книг, выпущенных конкретным издательством.
 * Содержит основные данные (ID, название) и обложку книги.
 * <p>
 * Благодаря аннотации {@link JsonNaming}, поля объекта будут сериализованы
 * в JSON в формате snake_case (например, {@code book_id}, {@code title}).
 *
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetBooksByPublisherId {

    /**
     * Уникальный идентификатор книги.
     */
    private Long bookId;

    /**
     * Название книги.
     */
    private String title;

    /**
     * Изображение (обложка) книги.
     * <p>
     * Может содержать информацию о URL или бинарных данных изображения,
     * в зависимости от реализации сущности {@link Image}.
     */
    private Image image;
}
