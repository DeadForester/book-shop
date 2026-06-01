package dev.bookservice.web.dto.publisher;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.book.GetBooksByPublisherId;
import lombok.Data;

import java.util.List;

/**
 * DTO для представления полной информации об издательстве.
 * <p>
 * Используется при получении детальных данных об издательстве, включая
 * список выпущенных им книг.
 * <p>
 * Благодаря аннотации {@link JsonNaming}, поля объекта будут сериализованы
 * в JSON в формате snake_case (например, {@code publisher_id}, {@code publisher_name}).
 *
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetPublisherById {

    /**
     * Уникальный идентификатор издательства.
     */
    private Long publisherId;

    /**
     * Название издательства.
     */
    private String publisherName;

    /**
     * Краткое описание или история издательства.
     */
    private String publisherDescription;

    /**
     * Список книг, выпущенных данным издательством.
     * <p>
     * Содержит краткую информацию о каждой книге (ID, название, обложка)
     * в виде DTO {@link GetBooksByPublisherId}.
     */
    private List<GetBooksByPublisherId> books;
}