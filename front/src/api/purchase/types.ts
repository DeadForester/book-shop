/*
    Example Value
    {
      "book_id": 0,
      "provider_id": 0,
      "quantity": 1,
      "total_sum": 0.01
    }
*/
export interface PurchaseValue {
    book_id: number;
    provider_id: number;
    quantity: number;
    total_sum: number;
}

export interface Purchase {
    purchase_id: number | string;
    purchase_num: number;
}

export interface PurchaseCreateResponse {
    message: string;
}
