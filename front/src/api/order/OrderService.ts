import axios from 'axios';
import { Order, OrderCreateResponse, OrderValue } from '@/api/order/types.ts';

export default class OrderService {
    static async getAllOrders() {
        return await axios.get<Order[]>('http://localhost:8080/api/v1/orders/me');
    }

    static async getOrderById(id: number | string) {
        return await axios.get<Order>(`http://localhost:8080/api/v1/orders/${id}`);
    }

    static async createOrder(order: OrderValue) {
        return await axios.post<OrderCreateResponse>(
            `http://localhost:8080/api/v1/orders/create`,
            order
        );
    }
}
