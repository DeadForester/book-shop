import { BookInOrder } from './BookInOrder.ts';

export interface OrderItem {
    orderItemId: number;
    book: BookInOrder;
    quantity: number;
}
