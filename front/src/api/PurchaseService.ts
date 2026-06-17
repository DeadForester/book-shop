import axios from 'axios';

import { API_URL } from '@/http';

import { Purchase } from '../../models/db/Purchase';
import { PurchaseCreate } from '../../models/request/Purchase/PurchaseCreate.ts';

export default class PurchaseService {
    static async getPurchaseById(id: number) {
        return await axios.get<Purchase>(`${API_URL}/purchases/${id}`);
    }

    static async createPurchase(purchase: PurchaseCreate) {
        return await axios.post<Purchase>(`${API_URL}/purchases/create`, purchase);
    }

    static async duplicatePurchase(id: number) {
        return await axios.post<Purchase>(`${API_URL}/purchases/add_supply?id=${id}`);
    }
}
