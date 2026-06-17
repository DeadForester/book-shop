import { BookItem } from '@/shared/types/BookItem';

import { PurchaseProvider } from './PurchaseProvider.ts';

export interface Purchase {
    purchaseId: number;
    book: BookItem;
    provider: PurchaseProvider;
    quantity: number;
    totalSum: number;
    createdAt: Date;
    arrivedAt: Date;
}



