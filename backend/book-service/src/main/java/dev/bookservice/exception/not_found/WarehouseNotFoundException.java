package dev.bookservice.exception.not_found;

public class WarehouseNotFoundException extends NotFoundException {
    public WarehouseNotFoundException(Long id) {
        super("Склад с ID " + id + " не найден");
    }
}
