package dev.bookservice.repository.user;

import dev.bookservice.entity.user.User;
import dev.bookservice.entity.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createNewUser(User user) {
        String sql = """
                INSERT INTO USRS (EMAIL, PASSWORD, ROLE)
                VALUES (?,?,?) RETURNING *
                """;
        return jdbcTemplate.queryForObject(
                sql,
                this::mapRowToEntity,
                user.getEmail(),
                user.getPassword(),
                user.getRole().name());
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        String sql = """
                SELECT u.USER_ID
                    , u.EMAIL
                    , u.PASSWORD
                    , u.ROLE
                FROM USRS u
                WHERE u.USER_ID = ?
                """;
        List<User> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                userId
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = """
                SELECT u.USER_ID
                    , u.EMAIL
                    , u.PASSWORD
                    , u.ROLE
                FROM USRS u
                WHERE u.EMAIL = ?
                """;
        List<User> results = jdbcTemplate.query(
                sql,
                this::mapRowToEntity,
                email
        );

        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    private User mapRowToEntity(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .userId(rs.getLong("USER_ID"))
                .email(rs.getString("EMAIL"))
                .password(rs.getString("PASSWORD"))
                .role(UserRole.valueOf(rs.getString("ROLE")))
                .build();
    }
}