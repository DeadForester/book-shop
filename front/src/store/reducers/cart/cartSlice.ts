import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { Book } from '@/models/db/book';
import { CartState } from '@/models/store/cart/CartState.ts';

const initialState: CartState = {
    items: [],
    isOpen: false,
};

export const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {
        addToCart: (state, action: PayloadAction<Book>) => {
            const existing = state.items.find((item) => item.book.id === action.payload.id);

            if (existing) {
                existing.quantity += 1;
            } else {
                state.items.push({ id: action.payload.id, book: action.payload, quantity: 1 });
            }
        },

        removeFromCart: (state, action: PayloadAction<number>) => {
            state.items = state.items.filter((item) => item.book.id !== action.payload);
        },

        updateQuantity: (state, action: PayloadAction<{ bookId: number; quantity: number }>) => {
            const item = state.items.find((i) => i.book.id === action.payload.bookId);
            if (item) {
                item.quantity = action.payload.quantity;
            }
        },

        toggleCart: (state) => {
            state.isOpen = !state.isOpen;
        },

        clearCart: (state) => {
            state.items = [];
        },
    },
});

export const { addToCart, removeFromCart, updateQuantity, toggleCart, clearCart } =
    cartSlice.actions;

export default cartSlice.reducer;
