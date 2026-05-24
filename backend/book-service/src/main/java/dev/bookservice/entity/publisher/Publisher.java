package dev.bookservice.entity.publisher;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Сущность, представляющая издательство (publisher) в системе.
 * <p>
 * Соответствует таблице {@code PUBLISHERS} в базе данных. Используется для хранения
 * информации об организациях, выпускающих книги, включая контактные данные и описание.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PUBLISHERS")
public class Publisher {
    /**
     * Уникальный идентификатор издательства (первичный ключ).
     */
    private Long publisherId;
    /**
     * Наименование издательства.
     */
    private String name;
    /**
     * Описание издательства (специализация, история, ключевые направления).
     */
    private String description;
    /**
     * Контактный телефон издательства.
     */
    private String phone;
    /**
     * Юридический или фактический адрес издательства.
     */
    private String address;
}
