import { CheckCircle, ShoppingCart, WarningAmber } from '@mui/icons-material';
import { Alert, Box, Button, Card, CardContent, Snackbar, Typography } from '@mui/material';
import { Dispatch, SetStateAction, useState } from 'react';

import { OrderSummary } from '@/components/supplier-order-page/types.ts';
import { SnackBar } from '@/shared/types/SnackBar.ts';

interface SummarizePanelProps {
    orderSummary: OrderSummary;
    setOrderItems: Dispatch<SetStateAction<Record<string, number>>>;
    setSelectedSupplier: Dispatch<SetStateAction<string | null>>;
    setSearch: Dispatch<SetStateAction<string>>;
}

const SummarizePanel = ({
    orderSummary,
    setOrderItems,
    setSelectedSupplier,
    setSearch,
}: SummarizePanelProps) => {
    const [loading, setLoading] = useState(false);
    const [snackbar, setSnackbar] = useState<SnackBar>({
        open: false,
        message: '',
        severity: 'success',
    });

    const handleSubmitOrder = async () => {
        if (orderSummary.totalItems === 0) {
            setSnackbar({
                open: true,
                message: 'Добавьте хотя бы одну книгу',
                severity: 'warning',
            });
            return;
        }

        setLoading(true);
        try {
            await new Promise((res) => setTimeout(res, 1000));

            setSnackbar({
                open: true,
                message: `Заказ на ${orderSummary.totalItems} книг успешно отправлен!`,
                severity: 'success',
            });
            setOrderItems({});
            setSelectedSupplier(null);
            setSearch('');
        } catch (error) {
            setSnackbar({ open: true, message: 'Ошибка при отправке заказа', severity: 'error' });
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <Card
                variant="outlined"
                sx={{ position: 'sticky', bottom: 16, zIndex: 10, boxShadow: 4 }}
            >
                <CardContent
                    sx={{
                        display: 'flex',
                        flexWrap: 'wrap',
                        alignItems: 'center',
                        justifyContent: 'space-between',
                        gap: 2,
                        p: 2,
                    }}
                >
                    <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                        <ShoppingCart color="primary" fontSize="large" />
                        <Box>
                            <Typography variant="body1" sx={{ fontWeight: 'bold' }}>
                                Позиций: {orderSummary.totalItems}
                            </Typography>
                            <Typography
                                variant="h6"
                                color="primary.main"
                                sx={{ fontWeight: 'bold' }}
                            >
                                Сумма:{' '}
                                {orderSummary.totalCost.toLocaleString('ru-RU', {
                                    style: 'currency',
                                    currency: 'RUB',
                                })}
                            </Typography>
                        </Box>
                    </Box>
                    <Button
                        variant="contained"
                        size="large"
                        startIcon={loading ? null : <CheckCircle />}
                        onClick={handleSubmitOrder}
                        disabled={loading || orderSummary.totalItems === 0}
                        sx={{ px: 4, py: 1.2, fontSize: '1rem', borderRadius: 2 }}
                    >
                        {loading ? 'Отправка...' : 'Оформить заказ'}
                    </Button>
                </CardContent>
            </Card>
            <Snackbar
                open={snackbar.open}
                autoHideDuration={4000}
                onClose={() => setSnackbar({ ...snackbar, open: false })}
            >
                <Alert
                    severity={snackbar.severity}
                    sx={{ width: '100%' }}
                    iconMapping={{ warning: <WarningAmber /> }}
                >
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </>
    );
};

export default SummarizePanel;
