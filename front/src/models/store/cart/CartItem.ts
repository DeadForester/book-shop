import { Book } from '@/models/db/book';

export interface CartItem {
    id: number;
    book: Book;
    quantity: number;
}