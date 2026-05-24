package dev.bookservice.web.mapper.book;

import dev.bookservice.entity.book.Book;
import dev.bookservice.web.dto.author.GetAuthorsByBookId;
import dev.bookservice.web.dto.book.GetAllBooks;
import dev.bookservice.web.dto.book.GetBookById;
import dev.bookservice.web.dto.image.GetImageByBookId;
import dev.bookservice.web.dto.publisher.GetPublishersByBookId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Маппер для преобразования сущности {@link Book} и связанных данных в DTO.
 * Использует модель компонентов Spring ({@code componentModel = "spring"}) для автоматического внедрения.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * Собирает DTO {@link GetBookById} из сущности книги, изображения, списка издательств и списка авторов.
     * <p>
     * Выполняет маппинг полей с учётом явных правил и безопасную обработку {@code null}-значений.
     *
     * @param book       сущность книги
     * @param img        DTO изображения
     * @param publishers список DTO издательств
     * @param authors    список DTO авторов
     * @return собранный DTO {@link GetBookById}
     */
    @Mapping(target = "binding", expression = "java(book.getBinding().getName())")
    @Mapping(target = "image", source = "img")
    @Mapping(target = "publishers", source = "publishers")
    @Mapping(target = "authors", source = "authors")
    @Mapping(source = "book.bookId", target = "id")
    GetBookById toDtoBookById(Book book, GetImageByBookId img, List<GetPublishersByBookId> publishers, List<GetAuthorsByBookId> authors);

    /**
     * Преобразует сущность книги, изображение и список авторов в DTO {@link GetAllBooks}.
     * <p>
     * Используется для формирования краткой информации о книге в общих списках.
     *
     * @param book    сущность книги
     * @param img     DTO изображения
     * @param authors список DTO авторов
     * @return собранный DTO {@link GetAllBooks}
     */
    @Mapping(target = "image", source = "img")
    @Mapping(target = "authors", source = "authors")
    @Mapping(source = "book.bookId", target = "id")
    GetAllBooks toDtoAllBooks(Book book, GetImageByBookId img, List<GetAuthorsByBookId> authors);
}
