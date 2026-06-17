import { BookItem } from '@/shared/types/BookItem.ts';

export interface Warehouse {
    warehouseId: number;
    address: string;
    book: BookItem;
    quantity: number;
}