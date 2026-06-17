import axios from 'axios';

import { API_URL } from '@/http';
import { Order } from '@/models/db/order';
import { OrderCreate } from '@/models/request/order/OrderCreate.ts';

export default class OrderService {
    static async getAllOrders() {
        return await axios.get<Order[]>(`${API_URL}/orders/me`);
    }

    static async getOrderById(id: number) {
        return await axios.get<Order>(`${API_URL}/orders/${id}`);
    }

    static async createOrder(order: OrderCreate) {
        return await axios.post<Order>(`${API_URL}/orders/create`, order);
    }
}
