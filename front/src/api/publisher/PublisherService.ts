import axios from 'axios';
import { Publisher } from './types.ts';

export default class PublisherService {
    static async getPublisherById(id: number | string) {
        return await axios.get<Publisher[]>(`http://localhost:8080/api/v1/publishers/${id}`);
    }
}
