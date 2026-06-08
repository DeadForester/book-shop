import { ShoppingCart as OrderIcon } from '@mui/icons-material';
import { Alert, Container } from '@mui/material';

const HistoryEmpty = () => {
    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Alert severity="info" icon={<OrderIcon />}>
                У вас пока нет заказов. Начните покупки в нашем магазине!
            </Alert>
        </Container>
    );
};

export default HistoryEmpty;
