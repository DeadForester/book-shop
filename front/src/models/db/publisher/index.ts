import { PublisherBook } from './PublisherBook.ts';

export interface Publisher {
    publisherId: number;
    publisherName: string;
    publisherDescription: string;
    books: PublisherBook[];
}
