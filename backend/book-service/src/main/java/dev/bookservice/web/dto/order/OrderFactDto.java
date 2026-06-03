package dev.bookservice.web.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для загрузки факта заказа в ClickHouse.
 * <p>
 * Содержит денормализованные данные заказа для быстрого выполнения аналитических запросов.
 *
 * @param orderId        уникальный идентификатор заказа
 * @param orderNumber    публичный номер заказа (формат ddMMyyyy + 4 цифры)
 * @param userId         идентификатор пользователя
 * @param userEmail      email пользователя
 * @param orderDate      дата оформления заказа
 * @param status         текущий статус заказа
 * @param totalPrice     итоговая сумма заказа
 * @param itemsCount     количество позиций в заказе
 * @param booksCount     количество уникальных книг
 * @param totalBooks     суммарное количество экземпляров
 * @param createdAt      дата создания
 * @param modifiedAt     дата последнего изменения
 */
public record OrderFactDto(
        Long orderId,
        String orderNumber,
        Long userId,
        String userEmail,
        LocalDateTime orderDate,
        String status,
        BigDecimal totalPrice,
        Integer itemsCount,
        Integer booksCount,
        Integer totalBooks,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
