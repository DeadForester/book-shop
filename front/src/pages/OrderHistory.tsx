import { Box, Container, Typography } from '@mui/material';
import { ChangeEvent, useState } from 'react';

import HistoryEmpty from '../components/order-history-page/HistoryEmpty.tsx';
import HistoryList from '../components/order-history-page/HistoryList.tsx';
import { generateMockOrders } from '../data/generateMockOrders.ts';
import PaginationControls from '../shared/components/PaginationControls.tsx';

export default function OrderHistory() {
    const [orders] = useState(() => generateMockOrders(25));
    const [currentPage, setCurrentPage] = useState(1);

    const ITEMS_PER_PAGE = 9;

    const totalPages = Math.ceil(orders.length / ITEMS_PER_PAGE);
    const currentOrders = orders.slice(
        (currentPage - 1) * ITEMS_PER_PAGE,
        currentPage * ITEMS_PER_PAGE
    );

    const handlePageChange = (_event: ChangeEvent<unknown>, page: number) => {
        setCurrentPage(page);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    if (orders.length === 0) {
        return <HistoryEmpty />;
    }

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Box sx={{ mb: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom sx={{ fontWeight: 'bold' }}>
                    История заказов
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Всего заказов: {orders.length}
                </Typography>
            </Box>

            <HistoryList orders={currentOrders} />

            <PaginationControls
                page={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />
        </Container>
    );
}
