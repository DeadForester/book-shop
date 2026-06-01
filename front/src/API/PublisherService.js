import axios from 'axios';

export default class PublisherService {
    static async getPublisherById(id) {
        return await axios.get(`http://localhost:8080/api/v1/publishers/${id}`);
    }
}
