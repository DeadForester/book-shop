import { BookInOrder } from '@/shared/types/mock/BookInOrder.ts';

export interface Order {
    id: string;
    date: string;
    total: number;
    items: BookInOrder[];
}