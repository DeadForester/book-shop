import { BookImage } from '@/models/db/book/BookImage.ts';

export interface PublisherBook {
    bookId: number;
    title: string;
    image: BookImage;
}