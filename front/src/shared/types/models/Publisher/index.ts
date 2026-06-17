import { PublisherBook } from './PublisherBook';

export interface Publisher {
    publisherId: number;
    publisherName: string;
    publisherDescription: string;
    books: PublisherBook[];
}
