package dev.bookservice.exception.not_found;

public class WarehouseBookNotFoundException extends NotFoundException {
    public WarehouseBookNotFoundException(Long warehouseId, Long bookId) {
        super("Книга с ID " + bookId + " не найдена на складе " + warehouseId);
    }
}