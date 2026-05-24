package dev.bookservice.repository.author;

import dev.bookservice.author.Author;

import java.util.List;

/**
 * Репозиторий для доступа к данным авторов в базе данных.
 */
public interface AuthorRepository {

    /**
     * Возвращает список всех авторов, связанных с указанной книгой.
     *
     * @param bookId идентификатор книги
     * @return список сущностей {@link Author} или пустой список, если записи не найдены
     */
    List<Author> getAllAuthorsByBookId(Long bookId);
}
