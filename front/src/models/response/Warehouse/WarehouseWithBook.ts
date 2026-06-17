import { BookItem } from '@/shared/types/BookItem';

export interface WarehouseWithBook {
    warehouseId: number;
    book: BookItem;
    quantity: number;
}
