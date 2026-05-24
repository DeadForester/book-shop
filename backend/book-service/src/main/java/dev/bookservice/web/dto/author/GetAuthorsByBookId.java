package dev.bookservice.web.dto.author;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * DTO для передачи информации об авторе книги в ответах API.
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetAuthorsByBookId {
    /**
     * Уникальный идентификатор автора.
     */
    private Long authorId;

    /**
     * Имя автора.
     */
    private String firstname;

    /**
     * Фамилия автора.
     */
    private String surname;
}
