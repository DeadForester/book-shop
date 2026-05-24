package dev.bookservice.web.mapper.publisher;

import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.web.dto.publisher.GetPublishersByBookId;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования сущности {@link Publisher} в DTO {@link GetPublishersByBookId}.
 * <p>
 * Реализация генерируется автоматически библиотекой MapStruct.
 * Компонент регистрируется в контексте Spring благодаря {@code componentModel = "spring"}.
 */
@Mapper(componentModel = "spring")
public interface PublisherMapper {

    /**
     * Преобразует сущность издательства в DTO.
     * <p>
     * Поля сопоставляются автоматически по совпадению имён: {@code publisherId} → {@code publisherId}, {@code name} → {@code name}.
     *
     * @param entity сущность {@link Publisher} для преобразования
     * @return DTO {@link GetPublishersByBookId} или {@code null}, если {@code entity} равен {@code null}
     */
    GetPublishersByBookId toDto(Publisher entity);
}
