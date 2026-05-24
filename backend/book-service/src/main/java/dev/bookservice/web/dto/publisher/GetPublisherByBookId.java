package dev.bookservice.web.dto.publisher;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * DTO для передачи информации об издательстве, связанном с книгой.
 * <p>
 * Используется в ответах API при запросе детальной информации о книге.
 * Имена полей автоматически конвертируются в snake_case.
 *
 * @see JsonNaming
 * @see PropertyNamingStrategies.SnakeCaseStrategy
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetPublisherByBookId {

    /**
     * Уникальный идентификатор издательства.
     */
    private Long publisherId;

    /**
     * Наименование издательства.
     */
    private String name;
}
