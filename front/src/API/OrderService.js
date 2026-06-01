import axios from 'axios';

export default class OrderService {
    static async getAllOrders() {
        return await axios.get('http://localhost:8080/api/v1/orders/me');
    }

    static async getOrderById(id) {
        return await axios.get(`http://localhost:8080/api/v1/orders/${id}`);
    }

    static async createOrder(order) {
        return await axios.post(`http://localhost:8080/api/v1/orders/create`, order);
    }
}
