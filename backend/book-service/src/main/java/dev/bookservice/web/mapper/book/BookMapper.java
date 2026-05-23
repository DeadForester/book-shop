package dev.bookservice.web.mapper.book;

import dev.bookservice.entity.book.Book;
import dev.bookservice.web.dto.book.GetBookById;
import dev.bookservice.web.dto.image.GetImageByBookId;
import dev.bookservice.web.dto.publisher.GetPublisherByBookId;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Маппер для преобразования сущности {@link Book} и связанных данных в DTO {@link GetBookById}.
 * Использует модель компонентов Spring ({@code componentModel = "spring"}) для автоматического внедрения.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * Собирает DTO {@link GetBookById} из сущности книги, изображения и списка издательств.
     * <p>
     * Выполняет ручное копирование полей, безопасную обработку {@code null}-значений
     * и создание защитной копии списка издательств.
     *
     * @param book        сущность книги
     * @param image       DTO изображения
     * @param publishers  список DTO издательств
     * @return собранный DTO или {@code null}, если все входные параметры {@code null}
     */
    default GetBookById toDto(Book book, GetImageByBookId image, List<GetPublisherByBookId> publishers) {
        if (book == null && image == null && publishers == null) {
            return null;
        }

        GetBookById getBookById = new GetBookById();

        if (book != null) {
            getBookById.setId(book.getBookId());
            getBookById.setTitle(book.getTitle());
            getBookById.setGenre(book.getGenre());
            getBookById.setDescription(book.getDescription());
            getBookById.setPages(book.getPages());
            getBookById.setBinding(book.getBinding().getName());
        }
        if (image != null) {
            getBookById.setImage(image);
        }
        if (publishers != null) {
            getBookById.setPublishers(new ArrayList<>(publishers));
        }

        return getBookById;
    }
}
