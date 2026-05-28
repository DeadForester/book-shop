package dev.bookservice.web.mapper.order_item;

import dev.bookservice.entity.order.OrderItem;
import dev.bookservice.web.dto.book.GetBookByOrderItem;
import dev.bookservice.web.dto.order_item.GetOrderItemByOrderId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования сущности {@link dev.bookservice.entity.order.OrderItem} в DTO {@link dev.bookservice.web.dto.order_item.GetOrderItemByOrderId}.
 * <p>
 * Реализация генерируется автоматически библиотекой MapStruct.
 * Компонент регистрируется в контексте Spring благодаря {@code componentModel = "spring"}.
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    /**
     * Преобразует сущность позиции заказа и связанную с ней книгу в DTO.
     * <p>
     * Поле {@code book} заполняется из переданного DTO книги.
     *
     * @param orderItem сущность позиции заказа
     * @param book      DTO книги, связанной с позицией
     * @return DTO {@link GetOrderItemByOrderId} с вложенной книгой
     */
    @Mapping(target = "book", source = "book")
    GetOrderItemByOrderId toDtoOrderItemByOrderId(OrderItem orderItem, GetBookByOrderItem book);
}
