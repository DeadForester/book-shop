import axios from 'axios';

import { API_URL } from '@/http';
import { Provider } from '@/shared/types/models/Provider';

export default class ProviderService {
    static async getProviderById(id: number) {
        return await axios.get<Provider>(`${API_URL}/provider/${id}`);
    }
}
