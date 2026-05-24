package dev.bookservice.web.mapper.image;

import dev.bookservice.entity.image.Image;
import dev.bookservice.web.dto.image.GetImageByBookId;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования сущности {@link Image} в DTO {@link GetImageByBookId}.
 * <p>
 * Реализация генерируется автоматически библиотекой MapStruct.
 * Интегрирован в контекст Spring через {@code componentModel = "spring"}.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper {

    /**
     * Преобразует сущность изображения в DTO.
     * <p>
     * Автоматически сопоставляет поля с одинаковыми именами: {@code imageId} → {@code imageId}, {@code url} → {@code url}.
     *
     * @param entity сущность {@link Image} для маппинга
     * @return DTO {@link GetImageByBookId} или {@code null}, если входной параметр {@code null}
     */
    GetImageByBookId toDto(Image entity);
}
