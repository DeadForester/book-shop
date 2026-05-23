package dev.bookservice.repository.publisher;

import dev.bookservice.entity.publisher.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcPublisherRepository implements PublisherRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Publisher> getPublisherByBookId(Long bookId) {
        String sql = """
                SELECT p.publisher_id
                     , p.name
                     , p.description
                     , p.phone
                     , p.address
                FROM PUBLISHERS p
                JOIN PUBLISHERS_BOOKS pb on pb.PUBLISHER_ID = p.PUBLISHER_ID
                WHERE pb.BOOK_ID = ?
                """;
        return jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                bookId
        );
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
