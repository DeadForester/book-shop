package dev.bookservice.web.mapper.book;

import dev.bookservice.entity.book.Book;
import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.web.dto.author.GetAuthorsByBookId;
import dev.bookservice.web.dto.book.*;
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
     * @param book      сущность книги
     * @param img       DTO изображения
     * @param publisher DTO издательства
     * @param authors   список DTO авторов
     * @return собранный DTO {@link GetBookById}
     */
    @Mapping(target = "binding", expression = "java(book.getBinding().getName())")
    @Mapping(target = "image", source = "img")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "authors", source = "authors")
    @Mapping(source = "book.bookId", target = "id")
    GetBookById toDtoBookById(Book book, GetImageByBookId img, GetPublishersByBookId publisher, List<GetAuthorsByBookId> authors);

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

    /**
     * Преобразует сущность книги в DTO для позиции заказа.
     *
     * @param book сущность книги
     * @return DTO {@link GetBookByOrderItem}
     */
    GetBookByOrderItem toBookByOrderItem(Book book);


    /**
     * Преобразует сущность книги и её изображение в DTO для отображения в списке книг издательства.
     * <p>
     * Используется при фильтрации или просмотре книг конкретного издателя.
     *
     * @param book сущность книги
     * @param img  DTO изображения книги
     * @return DTO {@link GetBooksByPublisherId}
     */
    @Mapping(target = "image", source = "img")
    GetBooksByPublisherId toBooksByPublisherByPublisherId(Book book, GetImageByBookId img);

    /**
     * Преобразует сущность книги и издательства в DTO для оформления покупки.
     * <p>
     * Используется в корзине или на этапе оформления заказа для отображения
     * ключевой информации о товаре вместе с данными издателя.
     * <p>
     * Алгоритм маппинга:
     * <ol>
     *     <li>Маппит идентификатор книги ({@code book.bookId}) в поле {@code id};</li>
     *     <li>Маппит название издательства ({@code publisher.name}) в поле {@code publisherName};</li>
     *     <li>Остальные поля преобразуются автоматически на основе совпадающих имён.</li>
     * </ol>
     *
     * @param book      сущность книги
     * @param publisher сущность издательства, выпустившего книгу
     * @return DTO {@link GetBookForPurchase}, готовый для отображения в чеке или корзине
     */
    @Mapping(target = "id", source = "book.bookId")
    @Mapping(target = "publisherName", source = "publisher.name")
    GetBookForPurchase toBookForPurchase(Book book, Publisher publisher);
}
