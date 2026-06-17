import axios from 'axios';

import { API_URL } from '@/http';
import { Book } from '@/models/db/book';

export default class BookService {
    static async getAllBooks(page: number = 1, size: number = 10) {
        return await axios.get<Book[]>(`${API_URL}/books?page=${page}&size=${size}`);
    }

    static async getBooksById(id: string | number) {
        return await axios.get<Book>(`${API_URL}/books/${id}`);
    }
}
