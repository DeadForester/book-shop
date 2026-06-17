import { Book } from '@/shared/types/models/Book';

export interface OrderSummary {
    totalItems: number;
    totalCost: number;
    itemsList: OrderItem[];
}

export interface OrderItem extends Book {
    quantity: number;
}
