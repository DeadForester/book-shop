package dev.bookservice.exception.bad_request;

public class DifferentTotalSumException extends BadRequestException {
    public DifferentTotalSumException(String message) {
        super(message);
    }
}
