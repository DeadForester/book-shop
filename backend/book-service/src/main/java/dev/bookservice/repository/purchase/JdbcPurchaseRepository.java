package dev.bookservice.repository.purchase;

import dev.bookservice.entity.purchase.Purchase;
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
public class JdbcPurchaseRepository implements PurchaseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Purchase> findById(Long id) {
        String sql = """
                SELECT p.PURCHASE_ID
                    , p.BOOK_ID
                    , p.PROVIDER_ID
                    , p.QUANTITY
                    , p.TOTAL_SUM
                    , p.CREATED_AT
                    , p.ARRIVED_AT
                FROM PURCHASES p
                WHERE p.PURCHASE_ID = ?
                """;

        List<Purchase> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                id
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public Purchase save(Purchase purchase) {
        String sql = """
                INSERT INTO PURCHASES (BOOK_ID, PROVIDER_ID, QUANTITY, TOTAL_SUM, CREATED_AT)
                VALUES (?, ?, ?, ?, ?)
                RETURNING *;
                """;

        return jdbcTemplate.queryForObject(
                sql,
                this::mapRowToEntity,
                purchase.getBookId(),
                purchase.getProviderId(),
                purchase.getQuantity(),
                purchase.getTotalSum(),
                purchase.getCreatedAt()
        );
    }

    @Override
    public Purchase saveArrivedTime(Long id, LocalDateTime now) {
        String sql = """
                UPDATE PURCHASES
                SET ARRIVED_AT = ?
                WHERE PURCHASE_ID = ?
                RETURNING *;
                """;

        return jdbcTemplate.queryForObject(
                sql,
                this::mapRowToEntity,
                now,
                id
        );
    }

    private Purchase mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Purchase.builder()
                .purchaseId(rs.getLong("PURCHASE_ID"))
                .bookId(rs.getLong("BOOK_ID"))
                .providerId(rs.getLong("PROVIDER_ID"))
                .quantity(rs.getLong("QUANTITY"))
                .totalSum(rs.getBigDecimal("TOTAL_SUM"))
                .createdAt(this.timeOfNullable(rs.getTimestamp("CREATED_AT")))
                .arrivedAt(this.timeOfNullable(rs.getTimestamp("ARRIVED_AT")))
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
