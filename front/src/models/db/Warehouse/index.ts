import { BookItem } from 'front/src/shared/types/BookItem.ts';

export interface Warehouse {
    warehouseId: number;
    address: string;
    book: BookItem;
    quantity: number;
}