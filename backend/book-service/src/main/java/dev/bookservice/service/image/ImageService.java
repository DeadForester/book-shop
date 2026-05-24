package dev.bookservice.service.image;

import dev.bookservice.entity.image.Image;
import dev.bookservice.exception.not_found.ImageNotFoundException;
import dev.bookservice.repository.image.ImageRepository;
import dev.bookservice.web.dto.image.GetImageByBookId;
import dev.bookservice.web.mapper.image.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Сервисный слой для работы с изображениями книг.
 * <p>
 * Инкапсулирует бизнес-логику получения и преобразования данных изображений.
 * Выступает посредником между слоем репозиториев ({@link ImageRepository}) и
 * слоем маппинга ({@link ImageMapper}), обеспечивая централизованную обработку
 * ошибок и логирование.
 *
 * @see Service
 * @see ImageRepository
 * @see ImageMapper
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    /**
     * Получает изображение, связанное с указанной книгой.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Фиксирует начало поиска в логах уровня {@code DEBUG};</li>
     *     <li>Выполняет запрос к репозиторию {@link ImageRepository#getImageByBookId(Long)};</li>
     *     <li>При наличии записи преобразует сущность {@link Image} в DTO через {@link ImageMapper#toDto(Image)};</li>
     *     <li>При отсутствии записи логирует ошибку уровня {@code ERROR} и выбрасывает {@link ImageNotFoundException}.</li>
     * </ol>
     *
     * @param bookId уникальный идентификатор книги, для которой требуется получить изображение
     * @return DTO {@link GetImageByBookId} с данными изображения
     * @throws ImageNotFoundException если изображение для указанного {@code bookId} не найдено в базе данных
     * @see ImageRepository#getImageByBookId(Long)
     * @see ImageMapper#toDto(Image)
     */
    public GetImageByBookId getImageByBookId(Long bookId) {
        log.debug("Поиск изображения по bookId={}", bookId);
        Image image = imageRepository.getImageByBookId(bookId).orElseThrow(
                () -> {
                    log.warn("Изображение по bookId={} не найдено", bookId);
                    return new ImageNotFoundException("Изображение по bookId=" + bookId + " не найдено");
                }
        );
        return imageMapper.toDto(image);
    }
}