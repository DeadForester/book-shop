import { Book } from '@/models/db/book';

export interface OrderSummary {
    totalItems: number;
    totalCost: number;
    itemsList: OrderItem[];
}

export interface OrderItem extends Book {
    quantity: number;
}
