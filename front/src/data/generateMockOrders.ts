import { Order } from '@/shared/types/mock/Order';
import { BookInOrder } from '@/shared/types/mock/BookInOrder.ts';

export const generateMockOrders = (count: number): Order[] => {
    const books = [
        '1984',
        'Мастер и Маргарита',
        'Гарри Поттер',
        'Война и мир',
        'Дюна',
        'Преступление и наказание',
    ];
    return Array.from(
        { length: count },
        (_, i): Order => ({
            id: `ORD-${String(i + 1).padStart(3, '0')}`,
            date: new Date(
                2024,
                Math.floor(Math.random() * 12),
                Math.floor(Math.random() * 28) + 1
            ).toLocaleDateString('ru-RU'),
            total: Math.floor(Math.random() * 5000) + 500,
            items: Array.from(
                { length: Math.floor(Math.random() * 3) + 1 },
                (): BookInOrder => ({
                    name: books[Math.floor(Math.random() * books.length)],
                    quantity: Math.floor(Math.random() * 3) + 1,
                })
            ),
        })
    );
};
