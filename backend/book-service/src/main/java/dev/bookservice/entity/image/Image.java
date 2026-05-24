package dev.bookservice.entity.image;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Сущность, представляющая изображение в системе.
 * <p>
 * Соответствует таблице {@code IMAGES} в базе данных. Используется для хранения
 * уникального идентификатора и ссылки (URL) на хранилище {@code MINIO}.
 *
 * @see Table
 * @see Id
 * @see Builder
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "IMAGES")
public class Image {
    /**
     * Уникальный идентификатор изображения (первичный ключ).
     */
    @Id
    private Long imageId;
    /**
     * Ссылка (URL) на изображение.
     */
    private String url;
}
