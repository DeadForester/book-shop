package dev.bookservice.repository.warehouse;

import dev.bookservice.entity.warehouse.WarehouseBook;
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
public class JdbcWarehouseBookRepository implements WarehouseBookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<WarehouseBook> findByWarehouseAndBook(Long warehouseId, Long bookId) {
        String sql = """
                SELECT wb.WAREHOUSE_ID
                    , wb.BOOK_ID
                    , wb.QUANTITY
                    , wb.CREATED_AT
                    , wb.MODIFIED_AT
                FROM WAREHOUSES_BOOKS wb
                WHERE wb.WAREHOUSE_ID = ?
                AND wb.BOOK_ID = ?
                """;

        List<WarehouseBook> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                warehouseId,
                bookId
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public void save(WarehouseBook warehouseBook, LocalDateTime now) {
        String sql = """
                INSERT INTO WAREHOUSES_BOOKS (BOOK_ID, QUANTITY, CREATED_AT)
                VALUES (?,?,?)
                """;
        jdbcTemplate.update(
                sql,
                warehouseBook.getBookId(),
                warehouseBook.getQuantity(),
                now
        );
    }


    private WarehouseBook mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return WarehouseBook.builder()
                .warehouseId(rs.getLong("WAREHOUSE_ID"))
                .bookId(rs.getLong("BOOK_ID"))
                .quantity(rs.getLong("QUANTITY"))
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
