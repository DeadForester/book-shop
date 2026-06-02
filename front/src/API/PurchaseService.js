import axios from 'axios';

export default class PurchaseService {
    static async getPurchaseById(id) {
        return await axios.get(`http://localhost:8080/api/v1/purchases/${id}`);
    }

    /*
        Example Value
        {
          "book_id": 0,
          "provider_id": 0,
          "quantity": 1,
          "total_sum": 0.01
        }
    */
    static async createPurchase(purchase) {
        return await axios.post(`http://localhost:8080/api/v1/purchases/create`, purchase);
    }

    static async duplicatePurchase(id) {
        return await axios.post(`http://localhost:8080/api/v1/purchases/add_supply?id=${id}`);
    }
}
