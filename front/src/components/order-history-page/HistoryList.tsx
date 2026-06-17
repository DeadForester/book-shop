import { Grid } from '@mui/material';

import { Order } from '@/shared/types/mock/Order';

import HistoryItem from './HistoryItem.tsx';

interface HistoryListProps {
    orders: Order[];
}

const HistoryList = ({ orders }: HistoryListProps) => {
    return (
        <Grid container spacing={3} sx={{ mb: 4 }}>
            {orders.map((order) => (
                <HistoryItem key={order.id} {...order} />
            ))}
        </Grid>
    );
};

export default HistoryList;
