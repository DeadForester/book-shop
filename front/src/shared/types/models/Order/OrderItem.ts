import { BookInOrder } from './BookInOrder';

export interface OrderItem {
    orderItemId: number;
    book: BookInOrder;
    quantity: number;
}
