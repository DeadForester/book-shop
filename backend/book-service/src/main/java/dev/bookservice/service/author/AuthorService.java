package dev.bookservice.service.author;

import dev.bookservice.entity.author.Author;
import dev.bookservice.exception.not_found.AuthorNotFoundException;
import dev.bookservice.repository.author.AuthorRepository;
import dev.bookservice.web.dto.author.GetAuthorsByBookId;
import dev.bookservice.web.mapper.author.AuthorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный слой для работы с данными авторов книг.
 * <p>
 * Инкапсулирует бизнес-логику поиска авторов, связанных с конкретной книгой,
 * и преобразования сущностей в DTO. Координирует взаимодействие между
 * {@link AuthorRepository} и {@link AuthorMapper}.
 *
 * @see Service
 * @see AuthorRepository
 * @see AuthorMapper
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    /**
     * Получает список авторов, связанных с указанной книгой.
     * <p>
     * Выполняет запрос к репозиторию, проверяет результат на пустоту
     * и преобразует найденные сущности в DTO {@link GetAuthorsByBookId}.
     *
     * @param bookId идентификатор книги
     * @return список DTO {@link GetAuthorsByBookId}
     * @throws AuthorNotFoundException если для указанного {@code bookId} не найдено ни одного автора
     * @see AuthorRepository#getAllAuthorsByBookId(Long)
     * @see AuthorMapper#toDtoAuthorsByBookId(Author)
     */
    public List<GetAuthorsByBookId> getAuthorsByBookId(Long bookId) {
        List<Author> authors = authorRepository.getAllAuthorsByBookId(bookId);
        if (authors.isEmpty()) {
            log.warn("По данной книге (bookId = {}) не найдено авторов", bookId);
            throw new AuthorNotFoundException("По данной книге (bookId = " + bookId + ") не найдено авторов");
        }

        return authors.stream()
                .map(authorMapper::toDtoAuthorsByBookId)
                .toList();
    }
}
