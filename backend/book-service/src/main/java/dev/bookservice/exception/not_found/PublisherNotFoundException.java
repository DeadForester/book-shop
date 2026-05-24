package dev.bookservice.exception.not_found;

public class PublisherNotFoundException extends NotFoundException {
    public PublisherNotFoundException(String message) {
        super(message);
    }
}
