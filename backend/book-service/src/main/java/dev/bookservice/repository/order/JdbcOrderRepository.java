package dev.bookservice.repository.order;

import dev.bookservice.entity.order.Order;
import dev.bookservice.entity.order.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Order> getOrderById(Long orderId, Long userId) {
        String sql = """
                SELECT o.ORDER_ID
                    , o.ORDER_NUMBER
                    , o.STATUS
                    , o.TOTAL_PRICE
                    , o.CREATED_AT
                    , o.MODIFIED_AT
                FROM ORDERS o
                WHERE o.ORDER_ID = ?
                AND o.USER_ID = ?
                """;

        List<Order> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                orderId,
                userId
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public Long createOrder(Order orderEntity, Long userId) {
        String sql = """
                INSERT INTO ORDERS (ORDER_NUMBER, USER_ID, STATUS, CREATED_AT)
                VALUES (?, ?, ?) RETURNING ORDER_ID;
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                orderEntity.getOrderNumber(),
                userId,
                getStringStatus(
                        orderEntity.getStatus()
                ),
                orderEntity.getCreatedAt()
        );
    }

    @Override
    public void updateOrderStatusByOrderId(Long orderId, BigDecimal totalPrice, Status status) {
        String sql = """
                UPDATE ORDERS
                SET STATUS = ?,
                    TOTAL_PRICE = ?
                WHERE ORDER_ID = ?
                """;

        jdbcTemplate.update(
                sql,
                getStringStatus(status),
                totalPrice,
                orderId
        );
    }

    @Override
    public List<Order> findAllOrdersByUserId(Long userId) {
        String sql = """
                SELECT o.ORDER_ID
                    , o.ORDER_NUMBER
                    , o.STATUS
                    , o.TOTAL_PRICE
                    , o.CREATED_AT
                    , o.MODIFIED_AT
                FROM ORDERS o
                WHERE o.USER_ID = ?
                """;

        List<Order> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                userId
        );

        return results.isEmpty() ? Collections.emptyList() : results;
    }

    private String getStringStatus(Status status) {
        return status.name();
    }

    private Order mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .orderId(rs.getLong("ORDER_ID"))
                .orderNumber(rs.getString("ORDER_NUMBER"))
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
