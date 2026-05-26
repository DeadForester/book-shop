package dev.bookservice.entity.author;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Сущность, представляющая автора книги.
 * <p>
 * Соответствует таблице {@code AUTHORS} в базе данных.
 * Использует Lombok для генерации стандартных методов.
 *
 * @see Table
 * @see Id
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "AUTHORS")
public class Author {

    /**
     * Уникальный идентификатор автора (первичный ключ).
     */
    @Id
    private Long authorId;

    /**
     * Имя автора.
     */
    private String firstname;

    /**
     * Фамилия автора.
     */
    private String surname;

    /**
     * Краткое описание или биография автора.
     */
    private String description;
}
