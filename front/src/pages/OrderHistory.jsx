import { useState } from 'react';
import { Box, Container, Typography } from '@mui/material';

import HistoryEmpty from '../components/order-history-page/HistoryEmpty.jsx';
import PaginationControls from '../shared/components/PaginationControls.jsx';
import HistoryList from '../components/order-history-page/HistoryList.jsx';

// Генерация тестовых данных
const generateMockOrders = (count) => {
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

const ITEMS_PER_PAGE = 9;

export default function OrderHistory() {
    const [orders] = useState(() => generateMockOrders(25));
    const [currentPage, setCurrentPage] = useState(1);

    const totalPages = Math.ceil(orders.length / ITEMS_PER_PAGE);
    const currentOrders = orders.slice(
        (currentPage - 1) * ITEMS_PER_PAGE,
        currentPage * ITEMS_PER_PAGE
    );

    const handlePageChange = (event, page) => {
        setCurrentPage(page);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    if (orders.length === 0) {
        return <HistoryEmpty />;
    }

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Box sx={{ mb: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom fontWeight="bold">
                    История заказов
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Всего заказов: {orders.length}
                </Typography>
            </Box>

            <HistoryList orders={currentOrders} />

            {totalPages > 1 && (
                <PaginationControls
                    page={currentPage}
                    totalPages={totalPages}
                    onPageChange={handlePageChange}
                />
            )}
        </Container>
    );
}
