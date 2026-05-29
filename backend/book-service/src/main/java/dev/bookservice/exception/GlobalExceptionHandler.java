package dev.bookservice.exception;

import dev.bookservice.exception.bad_request.BadRequestException;
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
        return createError(HttpStatus.NOT_FOUND, exception);
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
        return createError(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    /**
     * Обрабатывает исключения {@link BadRequestException}.
     * <p>
     * Возвращает статус 400 (Bad Request) и текст ошибки в теле ответа.
     * Используется при некорректных входных данных или нарушении бизнес-правил валидации.
     *
     * @param exception перехваченное исключение неверного запроса
     * @return HTTP-ответ со статусом 400
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> createBadRequestExceptionHandler(BadRequestException exception) {
        return createError(HttpStatus.BAD_REQUEST, exception);
    }

    /**
     * Формирует стандартный HTTP-ответ с кодом ошибки и сообщением.
     * <p>
     * Также выполняет логирование ошибки уровня {@code ERROR} с указанием статуса,
     * сообщения и класса исключения.
     *
     * @param status    HTTP-статус ответа
     * @param exception исходное исключение
     * @return объект {@link ResponseEntity} с текстом ошибки
     */
    private ResponseEntity<String> createError(HttpStatus status, Throwable exception) {
        String message = exception.getMessage();
        log.error("Ошибка = {}. Пользователю возвращен статус = {}. Класс ошибки = {}", message, status, exception.getClass());
        return ResponseEntity.status(status).body(exception.getMessage());
    }
}
