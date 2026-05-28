package dev.bookservice.exception.not_found;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
