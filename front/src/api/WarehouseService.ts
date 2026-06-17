import axios from 'axios';

import { API_URL } from '@/http';
import { AddBookToWarehouse } from '@/shared/types/request/Warehouse/AddBookToWarehouse.ts';
import { BookByWarehouse } from '@/shared/types/request/Warehouse/BookByWarehouse.ts';
import { WarehouseIdAddress } from '@/shared/types/response/Warehouse/WarehouseIdAddress.ts';
import { WarehouseWithBook } from '@/shared/types/response/Warehouse/WarehouseWithBook.ts';

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
