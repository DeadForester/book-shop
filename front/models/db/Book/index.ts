import { Author } from './Author';
import { BookImage } from './BookImage';

export interface Book {
    id: number;
    title: string;
    genre: string;
    image: BookImage;
    authors: Author[];
    description: string;
    pages: number;
    binding: string;
    price: number;
    provider: string;
    stock: number;
}
