package dev.bookservice.web.mapper.author;

import dev.bookservice.entity.author.Author;
import dev.bookservice.web.dto.author.GetAuthorsByBookId;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования сущности {@link Author} в DTO {@link GetAuthorsByBookId}.
 * <p>
 * Реализация генерируется автоматически библиотекой MapStruct.
 * Компонент регистрируется в контексте Spring через {@code componentModel = "spring"}.
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    /**
     * Преобразует сущность автора в DTO.
     * <p>
     * Поля сопоставляются автоматически по совпадению имён.
     *
     * @param author сущность {@link Author} для преобразования
     * @return DTO {@link GetAuthorsByBookId} или {@code null}, если {@code author} равен {@code null}
     */
    GetAuthorsByBookId toDtoAuthorsByBookId(Author author);
}
