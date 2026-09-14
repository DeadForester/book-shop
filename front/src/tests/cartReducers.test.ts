import { beforeEach, describe, expect, test } from 'vitest';

import { Book } from '@/models/db/book';
import cartReducer, {
    addToCart,
    clearCart,
    removeFromCart,
    toggleCart,
    updateQuantity,
} from '@/store/reducers/cart/cartSlice.ts';

describe('cartReducers', () => {
    let book: Book;

    beforeEach(() => {
        book = {
            id: 1,
            title: '451 градус',
            genre: 'Антиутопия',
            image: {
                imageId: 1,
                url: 'https://avatars.mds.yandex.net/i?id=f78bcd2f5e68f608054378b688f088c4_l-5664195-images-thumbs&n=13',
            },
            authors: [
                {
                    authorId: 1,
                    firstName: 'Рэй',
                    surname: 'Бредберри',
                },
            ],
            description: 'Книга',
            pages: 350,
            binding: 'string',
        };
    });

    test('add to cart', () => {
        expect(
            cartReducer(
                {
                    items: [],
                    isOpen: false,
                },
                addToCart(book)
            )
        ).toEqual({
            items: [
                {
                    id: 1,
                    book,
                    quantity: 1,
                },
            ],
            isOpen: false,
        });
    });

    test('remove from cart', () => {
        expect(
            cartReducer(
                {
                    items: [
                        {
                            id: 1,
                            book,
                            quantity: 1,
                        },
                    ],
                    isOpen: false,
                },
                removeFromCart(1)
            )
        ).toEqual({
            items: [],
            isOpen: false,
        });
    });

    test('update quantity', () => {
        expect(
            cartReducer(
                {
                    items: [
                        {
                            id: 1,
                            book,
                            quantity: 1,
                        },
                    ],
                    isOpen: false,
                },
                updateQuantity({ bookId: book.id, quantity: 2 })
            )
        ).toEqual({
            items: [
                {
                    id: 1,
                    book,
                    quantity: 2,
                },
            ],
            isOpen: false,
        });
    });

    test('clear cart', () => {
        expect(
            cartReducer(
                {
                    items: [
                        {
                            id: 1,
                            book,
                            quantity: 1,
                        },
                    ],
                    isOpen: false,
                },
                clearCart()
            )
        ).toEqual({
            items: [],
            isOpen: false,
        });
    });

    test('toggle cart', () => {
        expect(
            cartReducer(
                {
                    items: [],
                    isOpen: false,
                },
                toggleCart()
            )
        ).toEqual({
            items: [],
            isOpen: true,
        });

        expect(
            cartReducer(
                {
                    items: [],
                    isOpen: true,
                },
                toggleCart()
            )
        ).toEqual({
            items: [],
            isOpen: false,
        });
    });

    test('with empty state', () => {
        expect(cartReducer(undefined, addToCart(book))).toEqual({
            items: [
                {
                    id: 1,
                    book,
                    quantity: 1,
                },
            ],
            isOpen: false,
        });

        expect(cartReducer(undefined, removeFromCart(1))).toEqual({
            items: [],
            isOpen: false,
        });

        expect(cartReducer(undefined, updateQuantity({ bookId: 1, quantity: 1 }))).toEqual({
            items: [],
            isOpen: false,
        });

        expect(cartReducer(undefined, clearCart())).toEqual({
            items: [],
            isOpen: false,
        });

        expect(cartReducer(undefined, toggleCart())).toEqual({
            items: [],
            isOpen: true,
        });
    });
});
