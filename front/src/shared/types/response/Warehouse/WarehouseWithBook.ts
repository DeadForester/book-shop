import { BookItem } from '@/shared/types/BookItem.ts';

export interface WarehouseWithBook {
    warehouseId: number;
    book: BookItem;
    quantity: number;
}
