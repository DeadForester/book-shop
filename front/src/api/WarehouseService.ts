import axios from 'axios';

import { API_URL } from '@/http';
import { AddBookToWarehouse } from '@/models/request/warehouse/AddBookToWarehouse.ts';
import { BookByWarehouse } from '@/models/request/warehouse/BookByWarehouse.ts';
import { WarehouseIdAddress } from '@/models/response/warehouse/WarehouseIdAddress.ts';
import { WarehouseWithBook } from '@/models/response/warehouse/WarehouseWithBook.ts';

export default class WarehouseService {
    static async addBookToWarehouse(value: AddBookToWarehouse) {
        return await axios.post<WarehouseWithBook>(`${API_URL}/orders/me`, value);
    }

    static async getWarehouseById(id: number) {
        return await axios.get<WarehouseIdAddress>(`${API_URL}/warehouses/${id}`);
    }

    static async getWarehouseBookById(value: BookByWarehouse) {
        return await axios.get<WarehouseWithBook>(
            `${API_URL}/warehouses/${value.warehouseId}/books/${value.bookId}`
        );
    }
}
