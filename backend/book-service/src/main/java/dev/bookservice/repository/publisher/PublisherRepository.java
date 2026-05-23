package dev.bookservice.repository.publisher;

import dev.bookservice.entity.publisher.Publisher;

import java.util.List;

/**
 * Репозиторий для доступа к данным издательств в базе данных.
 */
public interface PublisherRepository {

    /**
     * Возвращает список издательств, связанных с указанной книгой.
     *
     * @param bookId идентификатор книги
     * @return список сущностей {@link Publisher} или пустой список, если записи не найдены
     */
    List<Publisher> getPublisherByBookId(Long bookId);
}
