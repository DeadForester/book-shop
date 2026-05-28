package dev.bookservice.repository.order;

import dev.bookservice.entity.order.Order;
import dev.bookservice.entity.order.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        String sql = """
                SELECT o.ORDER_ID
                    , o.ORDER_NUMBER
                    , o.STATUS
                    , o.TOTAL_PRICE
                    , o.CREATED_AT
                    , o.MODIFIED_AT
                FROM ORDERS o
                WHERE o.ORDER_ID = ?
                """;

        List<Order> orders = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                orderId
        );

        return Optional.ofNullable(orders.getFirst());
    }

    private Order mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .orderId(rs.getLong("ORDER_ID"))
                .orderNumber(rs.getLong("ORDER_NUMBER"))
                .status(Status.valueOf(rs.getString("STATUS")))
                .totalPrice(rs.getBigDecimal("TOTAL_PRICE"))
                .createdAt(timeOfNullable(rs.getTimestamp("CREATED_AT")))
                .modifiedAt(timeOfNullable(rs.getTimestamp("MODIFIED_AT")))
                .build();
    }

    private LocalDateTime timeOfNullable(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toLocalDateTime();
        }
    }
}
