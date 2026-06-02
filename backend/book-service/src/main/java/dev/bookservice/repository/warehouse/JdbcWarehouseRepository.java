package dev.bookservice.repository.warehouse;

import dev.bookservice.entity.warehouse.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcWarehouseRepository implements WarehouseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Warehouse> findById(Long id) {
        String sql = """
                SELECT wh.WAREHOUSE_ID
                    , wh.ADDRESS
                FROM WAREHOUSES wh
                WHERE wh.WAREHOUSE_ID = ?
                """;

        List<Warehouse> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                id
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    private Warehouse mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Warehouse.builder()
                .warehouseId(rs.getLong("WAREHOUSE_ID"))
                .address(rs.getString("ADDRESS"))
                .build();
    }
}
