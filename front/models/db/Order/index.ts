import { OrderItem } from './OrderItem';

export interface Order {
    orderId: number;
    orderNumber: string;
    orderItems: OrderItem[];
    status: string;
    totalPrice: number;
    created: Date;
}
