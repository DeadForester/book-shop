package dev.bookservice.service.purchase;

import dev.bookservice.entity.book.Book;
import dev.bookservice.entity.provider.Provider;
import dev.bookservice.entity.publisher.Publisher;
import dev.bookservice.entity.purchase.Purchase;
import dev.bookservice.exception.bad_request.BadRequestException;
import dev.bookservice.exception.not_found.BookNotFoundException;
import dev.bookservice.exception.not_found.ProviderNotFoundException;
import dev.bookservice.exception.not_found.PublisherNotFoundException;
import dev.bookservice.exception.not_found.PurchaseNotFoundException;
import dev.bookservice.repository.book.BookRepository;
import dev.bookservice.repository.provider.ProviderRepository;
import dev.bookservice.repository.publisher.PublisherRepository;
import dev.bookservice.repository.purchase.PurchaseRepository;
import dev.bookservice.web.dto.book.GetBookForPurchase;
import dev.bookservice.web.dto.provider.GetProviderForPurchase;
import dev.bookservice.web.dto.purchase.CreatePurchase;
import dev.bookservice.web.dto.purchase.GetPurchaseById;
import dev.bookservice.web.mapper.book.BookMapper;
import dev.bookservice.web.mapper.provider.ProviderMapper;
import dev.bookservice.web.mapper.purchase.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Сервисный слой для управления покупками (транзакциями).
 * <p>
 * Инкапсулирует бизнес-логику создания покупок, получения их детальной информации
 * и обработки поставок. Координирует работу репозиториев и мапперов для агрегации
 * данных о книге, поставщике и издательстве перед преобразованием в DTO.
 * <p>
 * Использует транзакционное управление для обеспечения целостности данных
 * при создании и обновлении записей о покупках.
 *
 * @see Service
 * @see PurchaseRepository
 * @see PurchaseMapper
 * @see BookMapper
 * @see ProviderMapper
 */
@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProviderRepository providerRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    private final PurchaseMapper purchaseMapper;
    private final BookMapper bookMapper;
    private final ProviderMapper providerMapper;

    private final static ZoneId ZONE_ID = ZoneId.of("Europe/Moscow");

    /**
     * Получает детальную информацию о покупке по её уникальному идентификатору.
     * <p>
     * Алгоритм выполнения (в режиме только для чтения):
     * <ol>
     *     <li>Запрашивает сущность {@link Purchase} через {@link PurchaseRepository#findById(Long)};</li>
     *     <li>Проверяет результат на наличие через {@link java.util.Optional};</li>
     *     <li>При отсутствии записи выбрасывает {@link PurchaseNotFoundException};</li>
     *     <li>Делегирует агрегацию связанных данных (книга, поставщик, издатель) в приватный метод {@link #getPublisherById(Purchase)};</li>
     *     <li>Возвращает итоговый DTO {@link GetPurchaseById}.</li>
     * </ol>
     *
     * @param id уникальный идентификатор покупки
     * @return DTO {@link GetPurchaseById} с полной информацией о покупке
     * @throws PurchaseNotFoundException если покупка с указанным {@code id} не найдена
     * @see PurchaseRepository#findById(Long)
     * @see #getPublisherById(Purchase)
     */
    @Transactional(readOnly = true)
    public GetPurchaseById getById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new PurchaseNotFoundException(id));

        return this.getPublisherById(purchase);
    }

    /**
     * Добавляет новую поставку (пополнение склада) на основе существующей покупки.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Проверяет входной параметр {@code id} на {@code null} и отрицательное значение; при ошибке выбрасывает {@link BadRequestException};</li>
     *     <li>Получает текущее время в часовом поясе {@code Europe/Moscow};</li>
     *     <li>Обновляет запись покупки в БД, фиксируя время прибытия через {@link PurchaseRepository#saveArrivedTime(Long, LocalDateTime)};</li>
     *     <li>Делегирует агрегацию связанных данных в приватный метод {@link #getPublisherById(Purchase)};</li>
     *     <li>Возвращает обновлённый DTO {@link GetPurchaseById} с актуальным значением {@code arrivedAt}.</li>
     * </ol>
     *
     * @param id уникальный идентификатор покупки, используемой как шаблон для поставки
     * @return DTO {@link GetPurchaseById} с данными обновлённой записи
     * @throws BadRequestException если параметр {@code id} некорректен
     * @throws PurchaseNotFoundException если покупка с указанным {@code id} не найдена
     * @see PurchaseRepository#saveArrivedTime(Long, LocalDateTime)
     * @see #getPublisherById(Purchase)
     */
    public GetPurchaseById addNewSupply(Long id) {
        if (id == null || id < 0L) {
            throw new BadRequestException("Укажите верный параметр");
        }

        LocalDateTime now = LocalDateTime.now(ZONE_ID);

        Purchase purchase = purchaseRepository.saveArrivedTime(id, now);

        return this.getPublisherById(purchase);
    }

    /**
     * Создает новую покупку на основе предоставленных данных.
     * <p>
     * Алгоритм выполнения (в транзакции):
     * <ol>
     *     <li>Проверяет существование поставщика через {@link ProviderRepository#getProviderById(Long)}; при отсутствии выбрасывает {@link ProviderNotFoundException};</li>
     *     <li>Проверяет существование книги через {@link BookRepository#getBookByBookId(Long)}; при отсутствии выбрасывает {@link BookNotFoundException};</li>
     *     <li>Преобразует входящий DTO {@link CreatePurchase} в сущность {@link Purchase} через {@link PurchaseMapper#toEntity(CreatePurchase)};</li>
     *     <li>Устанавливает время создания ({@code createdAt}) в текущий момент;</li>
     *     <li>Сохраняет сущность в БД через {@link PurchaseRepository#save(Purchase)};</li>
     *     <li>Возвращает полную информацию о созданной покупке через {@link #getById(Long)}.</li>
     * </ol>
     *
     * @param request DTO {@link CreatePurchase} с данными для оформления покупки
     * @return DTO {@link GetPurchaseById} с данными созданной покупки
     * @throws ProviderNotFoundException если поставщик с указанным {@code providerId} не найден
     * @throws BookNotFoundException если книга с указанным {@code bookId} не найдена
     * @see PurchaseMapper#toEntity(CreatePurchase)
     * @see PurchaseRepository#save(Purchase)
     * @see #getById(Long)
     */
    @Transactional
    public GetPurchaseById create(CreatePurchase request) {
        providerRepository.getProviderById(request.getProviderId())
                .orElseThrow(() -> new ProviderNotFoundException(request.getProviderId()));

        bookRepository.getBookByBookId(request.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Книга не найдена"));

        Purchase purchase = purchaseMapper.toEntity(request);
        purchase.setCreatedAt(LocalDateTime.now());

        Purchase saved = purchaseRepository.save(purchase);

        return getById(saved.getPurchaseId());
    }

    /**
     * Агрегирует данные о покупке, книге, поставщике и издательстве в итоговый DTO.
     * <p>
     * Вспомогательный приватный метод, используемый для формирования ответа.
     * <p>
     * Алгоритм выполнения:
     * <ol>
     *     <li>Загружает сущность {@link Book} по {@code purchase.getBookId()}; при отсутствии выбрасывает {@link BookNotFoundException};</li>
     *     <li>Загружает сущность {@link Provider} по {@code purchase.getProviderId()}; при отсутствии выбрасывает {@link ProviderNotFoundException};</li>
     *     <li>Загружает сущность {@link Publisher}, связанную с книгой; при отсутствии выбрасывает {@link PublisherNotFoundException};</li>
     *     <li>Преобразует книгу и издателя в DTO {@link GetBookForPurchase} через {@link BookMapper#toBookForPurchase(Book, Publisher)};</li>
     *     <li>Преобразует поставщика в DTO {@link GetProviderForPurchase} через {@link ProviderMapper#toProviderForPurchase(Provider)};</li>
     *     <li>Собирает финальный DTO {@link GetPurchaseById} через {@link PurchaseMapper#toGetPurchaseById(Purchase, GetBookForPurchase, GetProviderForPurchase)}.</li>
     * </ol>
     *
     * @param purchase сущность покупки, содержащая базовые данные транзакции
     * @return DTO {@link GetPurchaseById} с агрегированной информацией
     * @throws BookNotFoundException если книга не найдена
     * @throws ProviderNotFoundException если поставщик не найден
     * @throws PublisherNotFoundException если издатель не найден
     * @see BookMapper#toBookForPurchase(Book, Publisher)
     * @see ProviderMapper#toProviderForPurchase(Provider)
     * @see PurchaseMapper#toGetPurchaseById(Purchase, GetBookForPurchase, GetProviderForPurchase)
     */
    private GetPurchaseById getPublisherById(Purchase purchase) {
        Book book = bookRepository.getBookByBookId(purchase.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Книга с айди " + purchase.getBookId() + " не найдена"));

        Provider provider = providerRepository.getProviderById(purchase.getProviderId())
                .orElseThrow(() -> new ProviderNotFoundException(purchase.getProviderId()));

        Publisher publisher = publisherRepository.getPublisherByBookId(book.getBookId())
                .orElseThrow(() -> new PublisherNotFoundException("Издатель не найден"));

        GetBookForPurchase bookDto = bookMapper.toBookForPurchase(book, publisher);
        GetProviderForPurchase providerDto = providerMapper.toProviderForPurchase(provider);

        return purchaseMapper.toGetPurchaseById(purchase, bookDto, providerDto);
    }
}