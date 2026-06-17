import { BookItem } from '@/shared/types/BookItem';

export interface Warehouse {
    warehouseId: number;
    address: string;
    book: BookItem;
    quantity: number;
}