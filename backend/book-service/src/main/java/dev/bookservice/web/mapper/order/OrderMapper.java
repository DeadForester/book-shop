package dev.bookservice.web.mapper.order;

import dev.bookservice.entity.order.Order;
import dev.bookservice.web.dto.order.GetOrderById;
import dev.bookservice.web.dto.order_item.GetOrderItemByOrderId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Маппер для преобразования сущности {@link dev.bookservice.entity.order.Order} и связанных позиций заказа в DTO {@link dev.bookservice.web.dto.order.GetOrderById}.
 * <p>
 * Реализация генерируется автоматически библиотекой MapStruct.
 * Компонент регистрируется в контексте Spring благодаря {@code componentModel = "spring"}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * Преобразует сущность заказа и список позиций заказа в DTO.
     * <p>
     * Поле {@code orderItems} заполняется из переданного списка DTO позиций заказа.
     *
     * @param order      сущность заказа
     * @param orderItems список DTO позиций заказа
     * @return DTO {@link GetOrderById} с вложенными позициями заказа
     */
    @Mapping(target = "orderItems", source = "orderItems")
    GetOrderById toDtoOrderById(Order order, List<GetOrderItemByOrderId> orderItems);
}
