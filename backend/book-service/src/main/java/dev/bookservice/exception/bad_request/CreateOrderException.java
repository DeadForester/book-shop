package dev.bookservice.exception.bad_request;

public class CreateOrderException extends BadRequestException {
    public CreateOrderException(String message) {
        super(message);
    }
}
