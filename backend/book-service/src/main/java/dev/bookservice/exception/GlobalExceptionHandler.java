package dev.bookservice.exception;

import dev.bookservice.exception.not_found.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * Глобальный обработчик исключений для REST-контроллеров.
 * <p>
 * Перехватывает необработанные исключения и преобразует их в стандартизированные HTTP-ответы.
 *
 * @see RestControllerAdvice
 * @see ExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключения {@link NotFoundException}.
     * <p>
     * Возвращает статус 404 (Not Found) и текст ошибки в теле ответа.
     *
     * @param exception перехваченное исключение
     * @return HTTP-ответ со статусом 404
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException exception) {
        log.error("NOT_FOUND. Ошибка = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    /**
     * Обрабатывает исключения {@link SQLException}.
     * <p>
     * Возвращает статус 500 (Internal Server Error) и текст ошибки в теле ответа.
     *
     * @param exception перехваченное SQL-исключение
     * @return HTTP-ответ со статусом 500
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> sqlExceptionHandler(SQLException exception) {
        log.error("Ошибка при выполнении SQL запроса. Ошибка = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
