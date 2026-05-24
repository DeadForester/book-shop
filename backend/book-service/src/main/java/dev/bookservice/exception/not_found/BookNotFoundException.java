package dev.bookservice.exception.not_found;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
