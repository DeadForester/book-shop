import { BookImage } from '../Book/BookImage.ts';

export interface PublisherBook {
    bookId: number;
    title: string;
    image: BookImage;
}