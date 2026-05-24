package dev.bookservice.web.mapper.book;

import dev.bookservice.entity.book.Book;
import dev.bookservice.web.dto.book.GetAllBooks;
import dev.bookservice.web.dto.book.GetBookById;
import dev.bookservice.web.dto.image.GetImageByBookId;
import dev.bookservice.web.dto.publisher.GetPublisherByBookId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
     * @param book       сущность книги
     * @param img        DTO изображения
     * @param publishers список DTO издательств
     * @return собранный DTO или {@code null}, если все входные параметры {@code null}
     */
    @Mapping(target = "binding", expression = "java(book.getBinding().getName())")
    @Mapping(target = "image", source = "img")
    @Mapping(target = "publishers", source = "publishers")
    @Mapping(source = "book.bookId", target = "id")
    GetBookById toDtoBookById(Book book, GetImageByBookId img, List<GetPublisherByBookId> publishers);

    @Mapping(target = "image", source = "img")
    @Mapping(source = "book.bookId", target = "id")
    GetAllBooks toDtoAllBooks(Book book, GetImageByBookId img);
}
