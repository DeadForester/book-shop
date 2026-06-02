import axios from 'axios';

export default class OrderService {
    static async getAllOrders() {
        return await axios.get('http://localhost:8080/api/v1/orders/me');
    }

    static async getOrderById(id) {
        return await axios.get(`http://localhost:8080/api/v1/orders/${id}`);
    }

    /*
    Example Value
    {
      "total_price": 0.01,
      "order_items": [
        {
          "book": {
            "book_id": 0,
            "title": "string",
            "amount": 0
          },
          "quantity": 1
        }
      ]
    }
    */

    static async createOrder(order) {
        return await axios.post(`http://localhost:8080/api/v1/orders/create`, order);
    }
}
