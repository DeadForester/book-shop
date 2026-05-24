package dev.bookservice.web.dto.image;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.book.GetBookById;
import lombok.Data;

/**
 * DTO для представления информации об изображении книги в ответах API.
 * <p>
 * Используется как вложенный объект в {@link GetBookById}.
 * Все поля сериализуются в snake_case благодаря {@link JsonNaming}.
 *
 * @see JsonNaming
 * @see PropertyNamingStrategies.SnakeCaseStrategy
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetImageByBookId {

    /**
     * Уникальный идентификатор изображения.
     */
    private Long imageId;

    /**
     * URL-адрес для доступа к изображению.
     */
    private String url;
}
