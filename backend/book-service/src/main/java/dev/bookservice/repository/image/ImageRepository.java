package dev.bookservice.repository.image;

import dev.bookservice.entity.image.Image;

import java.util.Optional;

/**
 * Репозиторий для доступа к данным изображений в базе данных.
 */
public interface ImageRepository {

    /**
     * Ищет изображение, привязанное к указанной книге.
     * <p>
     * Возвращает {@link Optional} с найденной сущностью или пустой {@code Optional}, если изображение не найдено.
     *
     * @param bookId идентификатор книги
     * @return {@code Optional<Image>} с результатом поиска
     */
    Optional<Image> getImageByBookId(Long bookId);
}
