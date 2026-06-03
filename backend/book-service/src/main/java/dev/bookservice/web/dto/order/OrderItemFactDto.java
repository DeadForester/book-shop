package dev.bookservice.web.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderItemFactDto(
        Long orderItemId,
        Long orderId,
        LocalDateTime orderDate,
        Long userId,
        String orderStatus,
        Long bookId,
        String bookTitle,
        String genre,
        String authorNames,
        Long publisherId,
        String publisherName,
        Integer quantity,
        BigDecimal price,
        BigDecimal totalAmount
) {
}