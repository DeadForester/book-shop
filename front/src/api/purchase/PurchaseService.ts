import axios from 'axios';

import { Purchase, PurchaseCreateResponse, PurchaseValue } from '@/api/purchase/types.ts';

export default class PurchaseService {
    static async getPurchaseById(id: number | string) {
        return await axios.get<Purchase>(`http://localhost:8080/api/v1/purchases/${id}`);
    }

    static async createPurchase(purchase: PurchaseValue) {
        return await axios.post<PurchaseCreateResponse>(
            `http://localhost:8080/api/v1/purchases/create`,
            purchase
        );
    }

    static async duplicatePurchase(id: number | string) {
        return await axios.post<PurchaseCreateResponse>(
            `http://localhost:8080/api/v1/purchases/add_supply?id=${id}`
        );
    }
}
