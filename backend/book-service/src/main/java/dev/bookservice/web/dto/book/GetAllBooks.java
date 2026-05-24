package dev.bookservice.web.dto.book;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import dev.bookservice.web.dto.image.GetImageByBookId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO для представления краткой информации о книге в списках.
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetAllBooks {

    /**
     * Уникальный идентификатор книги.
     */
    private Long id;

    /**
     * Название книги.
     */
    private String title;

    /**
     * Изображение книги.
     */
    private GetImageByBookId image;

    /**
     * Стоимость книги.
     */
    private BigDecimal amount;

    /**
     * Жанр книги.
     */
    private String genre;
}
