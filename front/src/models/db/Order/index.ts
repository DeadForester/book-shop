import { OrderItem } from './OrderItem.ts';

export interface Order {
    orderId: number;
    orderNumber: string;
    orderItems: OrderItem[];
    status: string;
    totalPrice: number;
    created: Date;
}
