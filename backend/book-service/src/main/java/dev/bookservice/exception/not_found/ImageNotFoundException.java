package dev.bookservice.exception.not_found;

public class ImageNotFoundException extends NotFoundException {
    public ImageNotFoundException(String message) {
        super(message);
    }
}
