import axios from 'axios';

import { Book } from '@/shared/types/Book';

export default class BookService {
    static async getAllBooks(page: number = 1, size: number = 10) {
        return await axios.get<Book[]>(
            `http://localhost:8080/api/v1/books?page=${page}&size=${size}`
        );
    }

    static async getBooksById(id: string | number) {
        return await axios.get<Book>(`http://localhost:8080/api/v1/books/${id}`);
    }
}
