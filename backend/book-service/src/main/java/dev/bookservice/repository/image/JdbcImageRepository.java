package dev.bookservice.repository.image;

import dev.bookservice.entity.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcImageRepository implements ImageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Image> getImageByBookId(Long bookId) {
        String sql = """
                SELECT im.IMAGE_ID
                    ,im.URL
                FROM IMAGES im
                WHERE im.BOOK_ID = ?
                """;

        List<Image> images = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                bookId
        );
        return images.stream().findFirst();
    }

    private Image mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return Image.builder()
                .imageId(rs.getLong("IMAGE_ID"))
                .url(rs.getString("URL"))
                .build();
    }
}
