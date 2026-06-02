package dev.bookservice.exception.not_found;

public class PurchaseNotFoundException extends NotFoundException {
    public PurchaseNotFoundException(Long id) {
        super("Закупка с ID " + id + " не найдена");
    }
}
