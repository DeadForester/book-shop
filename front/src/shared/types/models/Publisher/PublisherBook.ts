import { BookImage } from '@/shared/types/models/Book/BookImage.ts';

export interface PublisherBook {
    bookId: number;
    title: string;
    image: BookImage;
}