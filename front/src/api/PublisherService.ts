import axios from 'axios';

import { API_URL } from '@/http';

import { Publisher } from '../../models/db/Publisher';

export default class PublisherService {
    static async getPublisherById(id: number) {
        return await axios.get<Publisher[]>(`${API_URL}/publishers/${id}`);
    }
}
