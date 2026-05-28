import { useState } from 'react';
import { Box, Container, Typography } from '@mui/material';

import HistoryEmpty from '../components/order-history-page/HistoryEmpty.jsx';
import PaginationControls from '../shared/components/PaginationControls.jsx';
import HistoryList from '../components/order-history-page/HistoryList.jsx';
import { generateMockOrders } from '../data/generateMockOrders.js';

export default function OrderHistory() {
    const [orders] = useState(() => generateMockOrders(25));
    const [currentPage, setCurrentPage] = useState(1);

    const ITEMS_PER_PAGE = 9;

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
