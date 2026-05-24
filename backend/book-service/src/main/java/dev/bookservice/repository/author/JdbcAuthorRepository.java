package dev.bookservice.repository.author;

import dev.bookservice.author.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Author> getAllAuthorsByBookId(Long bookId) {
        String sql = """
                SELECT a.AUTHOR_ID
                    , a.FIRSTNAME
                    , a.SURNAME
                    , a.DESCRIPTION
                FROM AUTHORS a
                JOIN AUTHORS_BOOKS ab on ab.AUTHOR_ID = a.AUTHOR_ID
                WHERE ab.BOOK_ID = ?
                """;
        List<Author> authors = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                bookId
        );

        return authors.isEmpty() ? Collections.emptyList() : authors;
    }

    private Author mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Author.builder()
                .authorId(rs.getLong("AUTHOR_ID"))
                .firstname(rs.getString("FIRSTNAME"))
                .surname(rs.getString("SURNAME"))
                .surname(rs.getString("DESCRIPTION"))
                .build();
    }
}
