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

    /**
     * Ищет издательство по уникальному идентификатору.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional},
     * если издательство с указанным идентификатором не найдено.
     *
     * @param publisherId уникальный идентификатор издательства
     * @return {@code Optional<Publisher>} с результатом поиска
     */
    Optional<Publisher> getPublisherById(Long publisherId);
}
