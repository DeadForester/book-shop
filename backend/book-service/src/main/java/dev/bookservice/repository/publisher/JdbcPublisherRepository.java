package dev.bookservice.repository.publisher;

import dev.bookservice.entity.publisher.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcPublisherRepository implements PublisherRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Publisher> getPublisherByBookId(Long bookId) {
        String sql = """
                SELECT p.publisher_id
                     , p.name
                     , p.description
                     , p.phone
                     , p.address
                FROM PUBLISHERS p
                JOIN BOOKS b on b.PUBLISHER_ID = p.PUBLISHER_ID
                WHERE b.BOOK_ID = ?
                """;
        List<Publisher> publishers = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                bookId
        );

        return publishers.isEmpty() ? Optional.empty() : Optional.ofNullable(publishers.getFirst());
    }

    private Publisher mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Publisher.builder()
                .publisherId(rs.getLong("PUBLISHER_ID"))
                .name(rs.getString("NAME"))
                .description(rs.getString("DESCRIPTION"))
                .phone(rs.getString("PHONE"))
                .address(rs.getString("ADDRESS"))
                .build();
    }
}
