package dev.bookservice.repository.book;

import dev.bookservice.entity.book.Book;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Book} в базе данных.
 */
public interface BookRepository {

    /**
     * Ищет книгу по уникальному идентификатору.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional}, если запись отсутствует.
     *
     * @param bookId идентификатор книги
     * @return {@code Optional<Book>} с результатом поиска
     */
    Optional<Book> getBookByBookId(Long bookId);

    /**
     * Возвращает список всех книг.
     *
     * @return список сущностей {@link Book} или пустой список, если записи отсутствуют
     */
    List<Book> findAllBooks();
}
