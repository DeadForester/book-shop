package dev.bookservice.repository.publisher;

import dev.bookservice.entity.publisher.Publisher;

import java.util.Optional;

/**
 * Репозиторий для доступа к данным издательств в базе данных.
 */
public interface PublisherRepository {

    /**
     * Возвращает издательство, связанное с указанной книгой.
     *
     * @param bookId идентификатор книги
     * @return Если запись найдена, то возвращается{@code Optional<Publisher>}. Или {@link Optional#empty()}, если запись не найдена
     */
    Optional<Publisher> getPublisherByBookId(Long bookId);
}
