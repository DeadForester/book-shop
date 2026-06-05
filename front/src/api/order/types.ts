/*
    Example Value
    {
      "total_price": 0.01,
      "order_items": [
        {
          "book": {
            "book_id": 0,
            "title": "string",
            "amount": 0
          },
          "quantity": 1
        }
      ]
    }
    */
export interface OrderValue {
    total_price: number;
    order_items: string;
    email: string;
}

export interface OrderItem {
    book: BookInOrder;
    quantity: number;
}

interface BookInOrder {
    book_id: number;
    title: string;
    amount: number;
}

export interface Order {
    order_id: number | string;
}

export interface OrderCreateResponse {
    message: string;
}
