import { BookItem } from 'front/src/shared/types/BookItem.ts';

export interface WarehouseWithBook {
    warehouseId: number;
    book: BookItem;
    quantity: number;
}
