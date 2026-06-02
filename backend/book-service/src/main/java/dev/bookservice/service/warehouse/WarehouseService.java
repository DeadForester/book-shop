package dev.bookservice.service.warehouse;

import dev.bookservice.entity.book.Book;
import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.entity.warehouse.Warehouse;
import dev.bookservice.entity.warehouse.WarehouseBook;
import dev.bookservice.exception.not_found.BookNotFoundException;
import dev.bookservice.exception.not_found.PublisherNotFoundException;
import dev.bookservice.exception.not_found.WarehouseBookNotFoundException;
import dev.bookservice.exception.not_found.WarehouseNotFoundException;
import dev.bookservice.repository.book.BookRepository;
import dev.bookservice.repository.publisher.PublisherRepository;
import dev.bookservice.repository.warehouse.WarehouseBookRepository;
import dev.bookservice.repository.warehouse.WarehouseRepository;
import dev.bookservice.web.dto.book.GetBookForPurchase;
import dev.bookservice.web.dto.warehouse.AddToWarehouseRequest;
import dev.bookservice.web.dto.warehouse.GetWarehouseBookInfo;
import dev.bookservice.web.dto.warehouse.GetWarehouseById;
import dev.bookservice.web.mapper.book.BookMapper;
import dev.bookservice.web.mapper.warehouse.WarehouseBookMapper;
import dev.bookservice.web.mapper.warehouse.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Сервисный слой для управления складами и складскими запасами.
 * <p>
 * Инкапсулирует бизнес-логику получения информации о складах, проверки наличия книг
 * и операций по пополнению запасов. Координирует работу репозиториев и мапперов
 * для агрегации данных о книге, издательстве и текущем количестве товара.
 * <p>
 * Использует транзакционное управление для обеспечения целостности данных
 * при обновлении складских остатков.
 *
 * @see Service
 * @see WarehouseRepository
 * @see WarehouseBookRepository
 * @see WarehouseMapper
 * @see WarehouseBookMapper
 */
@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseBookRepository warehouseBookRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    private final WarehouseMapper warehouseMapper;
    private final WarehouseBookMapper warehouseBookMapper;
    private final BookMapper bookMapper;

    private final static ZoneId ZONE_ID = ZoneId.of("Europe/Moscow");

    /**
     * Получает краткую информацию о складе по его уникальному идентификатору.
     * <p>
     * Алгоритм выполнения (в режиме только для чтения):
     * <ol>
     *     <li>Запрашивает сущность {@link Warehouse} через {@link WarehouseRepository#findById(Long)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи выбрасывает {@link WarehouseNotFoundException};</li>
     *     <li>Преобразует сущность в DTO через {@link WarehouseMapper#toGetWarehouseById(Warehouse)};</li>
     *     <li>Возвращает полученный DTO.</li>
     * </ol>
     *
     * @param warehouseId уникальный идентификатор склада
     * @return DTO {@link GetWarehouseById} с информацией о складе
     * @throws WarehouseNotFoundException если склад с указанным {@code warehouseId} не найден
     * @see WarehouseRepository#findById(Long)
     * @see WarehouseMapper#toGetWarehouseById(Warehouse)
     */
    @Transactional(readOnly = true)
    public GetWarehouseById getWarehouseInfo(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException(warehouseId));
        return warehouseMapper.toGetWarehouseById(warehouse);
    }

    /**
     * Получает информацию о наличии конкретной книги на указанном складе.
     * <p>
     * Алгоритм выполнения (в режиме только для чтения):
     * <ol>
     *     <li>Проверяет существование склада через {@link WarehouseRepository#findById(Long)}; при отсутствии выбрасывает {@link WarehouseNotFoundException};</li>
     *     <li>Загружает сущность {@link Book} через {@link BookRepository#getBookByBookId(Long)}; при отсутствии выбрасывает {@link BookNotFoundException};</li>
     *     <li>Ищет запись о наличии {@link WarehouseBook} через {@link WarehouseBookRepository#findByWarehouseAndBook(Long, Long)}; при отсутствии выбрасывает {@link WarehouseBookNotFoundException};</li>
     *     <li>Загружает сущность {@link Publisher}, связанную с книгой; при отсутствии выбрасывает {@link PublisherNotFoundException};</li>
     *     <li>Преобразует книгу и издателя в DTO {@link GetBookForPurchase} через {@link BookMapper#toBookForPurchase(Book, Publisher)};</li>
     *     <li>Собирает итоговый DTO {@link GetWarehouseBookInfo} через {@link WarehouseBookMapper#toGetWarehouseBookInfo(WarehouseBook, GetBookForPurchase)}.</li>
     * </ol>
     *
     * @param warehouseId уникальный идентификатор склада
     * @param bookId      уникальный идентификатор книги
     * @return DTO {@link GetWarehouseBookInfo} с данными о наличии
     * @throws WarehouseNotFoundException     если склад не найден
     * @throws BookNotFoundException          если книга не найдена
     * @throws PublisherNotFoundException     если издатель не найден
     * @throws WarehouseBookNotFoundException если книга отсутствует на данном складе
     * @see WarehouseBookRepository#findByWarehouseAndBook(Long, Long)
     * @see BookMapper#toBookForPurchase(Book, Publisher)
     * @see WarehouseBookMapper#toGetWarehouseBookInfo(WarehouseBook, GetBookForPurchase)
     */
    @Transactional(readOnly = true)
    public GetWarehouseBookInfo getStockInfo(Long warehouseId, Long bookId) {
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException(warehouseId));

        Book book = bookRepository.getBookByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException("Книга по id не найдена"));

        WarehouseBook wb = warehouseBookRepository.findByWarehouseAndBook(warehouseId, bookId)
                .orElseThrow(() -> new WarehouseBookNotFoundException(warehouseId, bookId));


        Publisher publisher = publisherRepository.getPublisherByBookId(bookId)
                .orElseThrow(() -> new PublisherNotFoundException("Издатель не найден"));

        GetBookForPurchase bookDto = bookMapper.toBookForPurchase(book, publisher);

        return warehouseBookMapper.toGetWarehouseBookInfo(wb, bookDto);
    }

    /**
     * Пополняет запасы книги на указанном складе.
     * <p>
     * Алгоритм выполнения (в транзакции):
     * <ol>
     *     <li>Получает текущее время в часовом поясе {@code Europe/Moscow};</li>
     *     <li>Проверяет существование склада через {@link WarehouseRepository#findById(Long)}; при отсутствии выбрасывает {@link WarehouseNotFoundException};</li>
     *     <li>Проверяет существование книги через {@link BookRepository#getBookByBookId(Long)}; при отсутствии выбрасывает {@link BookNotFoundException};</li>
     *     <li>Создает новую сущность {@link WarehouseBook} с переданным количеством через билдер;</li>
     *     <li>Сохраняет или обновляет запись в БД через {@link WarehouseBookRepository#save(WarehouseBook, LocalDateTime)};</li>
     *     <li>Возвращает актуальную информацию о наличии через {@link #getStockInfo(Long, Long)}.</li>
     * </ol>
     * <p>
     * <strong>Примечание:</strong> Реализация метода {@code save} в репозитории должна поддерживать
     * логику Upsert (обновление количества, если запись уже существует, или создание новой).
     *
     * @param request DTO {@link AddToWarehouseRequest} с данными для пополнения
     * @return DTO {@link GetWarehouseBookInfo} с обновленными данными о наличии
     * @throws WarehouseNotFoundException если склад не найден
     * @throws BookNotFoundException      если книга не найдена
     * @see WarehouseBookRepository#save(WarehouseBook, LocalDateTime)
     * @see #getStockInfo(Long, Long)
     */
    @Transactional
    public GetWarehouseBookInfo addToWarehouse(AddToWarehouseRequest request) {
        LocalDateTime now = LocalDateTime.now(ZONE_ID);

        warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException(request.getWarehouseId()));

        bookRepository.getBookByBookId(request.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Книга по id не найдена"));

        WarehouseBook wb = WarehouseBook.builder()
                .warehouseId(request.getWarehouseId())
                .bookId(request.getBookId())
                .quantity(request.getQuantityToAdd())
                .build();

        warehouseBookRepository.save(wb, now);

        return getStockInfo(request.getWarehouseId(), request.getBookId());
    }
}