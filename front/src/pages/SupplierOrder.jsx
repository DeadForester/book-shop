import { useMemo, useState } from 'react';
import {
    Autocomplete,
    Box,
    Card,
    CardContent,
    InputAdornment,
    TextField,
    Typography,
} from '@mui/material';
import { Business, Search } from '@mui/icons-material';
import { providers } from '../data/providers.ts';
import { BooksTable, SummarizePanel } from '../components/supplier-order-page';
import { goods } from '../data/goods.ts';

export default function SupplierOrder() {
    const [search, setSearch] = useState('');
    const [selectedSupplier, setSelectedSupplier] = useState(null);
    const [orderItems, setOrderItems] = useState({});

    const orderSummary = useMemo(() => {
        let totalItems = 0;
        let totalCost = 0;
        const itemsList = [];

        Object.entries(orderItems).forEach(([bookId, qty]) => {
            if (qty > 0) {
                const book = goods.find((b) => b.id === bookId);
                if (book) {
                    totalItems += qty;
                    totalCost += qty * book.price;
                    itemsList.push({ ...book, quantity: qty });
                }
            }
        });

        return { totalItems, totalCost, itemsList };
    }, [orderItems]);

    return (
        <Box
            sx={{ p: { xs: 2, md: 4 }, backgroundColor: 'background.default', minHeight: '100vh' }}
        >
            <Box sx={{ mb: 3 }}>
                <Typography variant="h4" fontWeight="bold" gutterBottom>
                    Заказ книг у поставщика
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Выберите поставщика, добавьте позиции и оформите заявку на пополнение склада
                </Typography>
            </Box>

            <Card variant="outlined" sx={{ mb: 3 }}>
                <CardContent
                    sx={{ p: 2, display: 'flex', flexWrap: 'wrap', gap: 2, alignItems: 'flex-end' }}
                >
                    <TextField
                        label="Поиск по названию или автору"
                        variant="outlined"
                        size="small"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        sx={{ minWidth: { xs: '100%', sm: 280 }, flex: 1 }}
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                    <Search color="action" />
                                </InputAdornment>
                            ),
                        }}
                    />
                    <Autocomplete
                        value={selectedSupplier}
                        onChange={(_, val) => setSelectedSupplier(val)}
                        options={providers}
                        renderInput={(params) => (
                            <TextField
                                {...params}
                                label="Поставщик"
                                variant="outlined"
                                size="small"
                                sx={{ minWidth: { xs: '100%', sm: 220 } }}
                                InputProps={{
                                    ...params.InputProps,
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            <Business color="action" />
                                        </InputAdornment>
                                    ),
                                }}
                            />
                        )}
                        clearOnEscape
                    />
                </CardContent>
            </Card>

            {/* 🔹 Таблица книг */}
            <BooksTable
                search={search}
                selectedSupplier={selectedSupplier}
                orderItems={orderItems}
                setOrderItems={setOrderItems}
            />

            <SummarizePanel
                orderSummary={orderSummary}
                setOrderItems={setOrderItems}
                setSelectedSupplier={setSelectedSupplier}
                setSearch={setSearch}
            />
        </Box>
    );
}
