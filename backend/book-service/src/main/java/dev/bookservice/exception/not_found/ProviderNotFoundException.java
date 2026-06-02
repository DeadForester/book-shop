package dev.bookservice.exception.not_found;

public class ProviderNotFoundException extends NotFoundException {
    public ProviderNotFoundException(Long id) {
        super("Поставщик с ID " + id + " не найден");
    }
}
