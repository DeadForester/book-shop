export const generateMockOrders = (count) => {
    const books = [
        '1984',
        'Мастер и Маргарита',
        'Гарри Поттер',
        'Война и мир',
        'Дюна',
        'Преступление и наказание',
    ];
    return Array.from({ length: count }, (_, i) => ({
        id: `ORD-${String(i + 1).padStart(3, '0')}`,
        date: new Date(
            2024,
            Math.floor(Math.random() * 12),
            Math.floor(Math.random() * 28) + 1
        ).toLocaleDateString('ru-RU'),
        total: Math.floor(Math.random() * 5000) + 500,
        items: Array.from({ length: Math.floor(Math.random() * 3) + 1 }, () => ({
            name: books[Math.floor(Math.random() * books.length)],
            qty: Math.floor(Math.random() * 3) + 1,
        })),
    }));
};
