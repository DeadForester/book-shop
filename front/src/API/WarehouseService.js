import axios from 'axios';

export default class WarehouseService {
    static async addBookToWarehouse(warehouseId, bookId, quantityToAdd) {
        return await axios.post('http://localhost:8080/api/v1/orders/me', {
            warehouseId,
            bookId,
            quantityToAdd
        });
    }

    static async getWarehouseById(id) {
        return await axios.get(`http://localhost:8080/api/v1/warehouses/${id}`);
    }

    static async getWarehouseBookById(warehouseId, bookId) {
        return await axios.get(
            `http://localhost:8080/api/v1/warehouses/${warehouseId}/books/${bookId}`
        );
    }
}