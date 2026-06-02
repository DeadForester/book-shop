package dev.bookservice.repository.provider;

import dev.bookservice.entity.provider.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcProviderRepository implements ProviderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Provider> getProviderById(Long id) {
        String sql = """
                SELECT pr.PROVIDER_ID
                    , pr.TITLE
                    , pr.ADDRESS
                    , pr.CONTACT_NUMBER
                FROM PROVIDERS pr
                WHERE pr.PROVIDER_ID =?
                """;

        List<Provider> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                id
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    private Provider mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Provider.builder()
                .providerId(rs.getLong("PROVIDER_ID"))
                .title(rs.getString("TITLE"))
                .address(rs.getString("ADDRESS"))
                .contactNumber(rs.getString("CONTACT_NUMBER"))
                .build();
    }
}
