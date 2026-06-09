import axios from 'axios';

import { BookByWarehouseValue, Warehouse, WarehouseValue } from '@/api/warehouse/types.ts';
import { Book } from '@/shared/types/Book.ts';

export default class WarehouseService {
    static async addBookToWarehouse(value: WarehouseValue) {
        return await axios.post('http://localhost:8080/api/v1/orders/me', value);
    }

    static async getWarehouseById(id: number | string) {
        return await axios.get<Warehouse>(`http://localhost:8080/api/v1/warehouses/${id}`);
    }

    static async getWarehouseBookById(value: BookByWarehouseValue) {
        return await axios.get<Book>(
            `http://localhost:8080/api/v1/warehouses/${value.warehouseId}/books/${value.bookId}`
        );
    }
}
