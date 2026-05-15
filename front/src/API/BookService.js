import axios from "axios";

export default class BookServiceService {
    static async getAll(limit = 10, page = 1) {
        return await axios.get(`https://jsonplaceholder.typicode.com/posts?_limit=${limit}&_page=${page}`);
    }

    static async getById(id) {
        return await axios.get(`https://jsonplaceholder.typicode.com/posts/${id}`);
    }
}