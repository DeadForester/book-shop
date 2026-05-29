package dev.bookservice.repository.book;

import dev.bookservice.entity.book.Binding;
import dev.bookservice.entity.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Book> getBookByBookId(Long bookId) {
        String sql = """
                SELECT b.BOOK_ID
                    , b.TITLE
                    , b.GENRE
                    , b.CREATION_YEAR
                    , b.PAGES
                    , b.DESCRIPTION
                    , b.AMOUNT
                    , b.BINDING
                    , b.CREATED_AT
                    , b.MODIFIED_AT
                FROM BOOKS b
                WHERE b.BOOK_ID = ?
                """;

        List<Book> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                bookId
        );
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public List<Book> findAllBooks(int offset, int size) {
        String sql = """
                SELECT *
                FROM BOOKS
                LIMIT ? OFFSET ?
                """;
        List<Book> allBooks = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                size,
                offset
        );
        return allBooks.isEmpty() ? Collections.emptyList() : allBooks;
    }

    @Override
    public Optional<Book> getBookByOrderItem(Long orderItemId) {
        String sql = """
                SELECT b.BOOK_ID
                    , b.TITLE
                    , b.GENRE
                    , b.CREATION_YEAR
                    , b.PAGES
                    , b.DESCRIPTION
                    , b.AMOUNT
                    , b.BINDING
                    , b.CREATED_AT
                    , b.MODIFIED_AT
                FROM BOOKS b
                JOIN ORDER_ITEMS oi on oi.BOOK_ID = b.BOOK_ID
                WHERE oi.ORDER_ITEM_ID = ?
                """;
        List<Book> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                orderItemId
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    private Book mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .bookId(rs.getLong("BOOK_ID"))
                .title(rs.getString("TITLE"))
                .genre(rs.getString("GENRE"))
                .creationYear(fromNumberToYear(rs.getShort("CREATION_YEAR")))
                .pages(rs.getShort("PAGES"))
                .description(rs.getString("DESCRIPTION"))
                .amount(rs.getBigDecimal("AMOUNT"))
                .binding(Binding.parseName(rs.getString("BINDING")))
                .createdAt(this.timeOfNullable(rs.getTimestamp("CREATED_AT")))
                .modifiedAt(this.timeOfNullable(rs.getTimestamp("MODIFIED_AT")))
                .build();
    }

    private Year fromNumberToYear(Short year) {
        CharSequence ch = new StringBuilder(String.valueOf(year));
        return Year.parse(ch);
    }

    private LocalDateTime timeOfNullable(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return timestamp.toLocalDateTime();
        }
    }
}
