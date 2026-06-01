import axios from "axios";

export default class BookService{
    static async getAllBooks(page = 1, limit = 10) {
        return await axios.get(`http://localhost:8080/api/v1/books?page=${page}&size=${limit}`);
    }

    static async getBooksById(id) {
        return await axios.get(`http://localhost:8080/api/v1/books/${id}`);
    }
}