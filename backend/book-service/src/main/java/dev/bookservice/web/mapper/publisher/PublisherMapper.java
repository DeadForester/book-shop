package dev.bookservice.web.mapper.publisher;

import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.web.dto.book.GetBooksByPublisherId;
import dev.bookservice.web.dto.publisher.GetPublisherById;
import dev.bookservice.web.dto.publisher.GetPublishersByBookId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

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
    GetPublishersByBookId toPublisherByBookId(Publisher entity);

    /**
     * Преобразует сущность издательства и список книг в DTO {@link GetPublisherById}.
     * <p>
     * Алгоритм маппинга:
     * <ol>
     *     <li>Извлекает идентификатор издательства из сущности {@link Publisher};</li>
     *     <li>Маппит поле {@code name} сущности в {@code publisherName} DTO;</li>
     *     <li>Маппит поле {@code description} сущности в {@code publisherDescription} DTO;</li>
     *     <li>Присваивает переданный список DTO книг {@code books} в соответствующее поле результата.</li>
     * </ol>
     *
     * @param publisher сущность издательства, содержащая основную информацию
     * @param books     список DTO {@link GetBooksByPublisherId}, представляющих книги этого издательства
     * @return собранный DTO {@link GetPublisherById}
     */
    @Mapping(target = "books", source = "books")
    @Mapping(target = "publisherName", source = "publisher.name")
    @Mapping(target = "publisherDescription", source = "publisher.description")
    GetPublisherById toPublisherById(Publisher publisher, List<GetBooksByPublisherId> books);
}
