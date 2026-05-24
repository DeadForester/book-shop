package dev.bookservice.entity.book;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;

/**
 * Сущность, представляющая книгу в системе.
 * <p>
 * Соответствует таблице {@code BOOKS} в базе данных и используется для
 * отображения записей о книгах в объектно-ориентированной модели приложения.
 *
 * @see Table
 * @see Id
 * @see Builder
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "BOOKS")
public class Book {

    /**
     * Уникальный идентификатор книги (первичный ключ).
     */
    @Id
    private Long bookId;

    /**
     * Название книги.
     */
    private String title;

    /**
     * Жанр книги.
     */
    private String genre;

    /**
     * Год издания книги.
     */
    private Year creationYear;

    /**
     * Количество страниц в книге.
     */
    private Short pages;

    /**
     * Описание книги (аннотация, синопсис).
     */
    private String description;

    /**
     * Тип переплёта книги.
     */
    private Binding binding;

    /**
     * Стоимость книги.
     */
    private BigDecimal amount;

    /**
     * Дата и время создания записи о книге.
     */
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего изменения записи о книге.
     */
    private LocalDateTime modifiedAt;
}
