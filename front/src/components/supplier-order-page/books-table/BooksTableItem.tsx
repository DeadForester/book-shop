import { Add, Remove } from '@mui/icons-material';
import { Box, Chip, IconButton, TableCell, TableRow, TextField, Typography } from '@mui/material';
import { Dispatch, SetStateAction } from 'react';

import { Book } from '@/shared/types/Book.ts';

interface BooksTableItemProps {
    book: Book;
    orderItems: Record<string, number>;
    setOrderItems: Dispatch<SetStateAction<Record<string, number>>>;
}

const BooksTableItem = ({ book, orderItems, setOrderItems }: BooksTableItemProps) => {
    const qty = orderItems[book.id] ?? 0;

    const handleQuantityChange = (bookId: string, delta: number) => {
        setOrderItems((prev) => {
            const current = prev[bookId] ?? 0;
            const next = Math.max(0, current + delta);

            if (next === 0) {
                const updated = { ...prev };
                delete updated[bookId];
                return updated;
            }

            return { ...prev, [bookId]: next };
        });
    };

    const handleDirectInput = (bookId: string, value: string) => {
        const qty = parseInt(value, 10) ?? 0;
        setOrderItems((prev) => {
            if (qty <= 0) {
                const updated = { ...prev };
                delete updated[bookId];
                return updated;
            }
            return { ...prev, [bookId]: qty };
        });
    };

    return (
        <TableRow key={book.id} hover>
            <TableCell>
                <Typography variant="body2" sx={{ fontWeight: 'bold' }}>
                    {book.name}
                </Typography>
                <Typography variant="caption" color="text.secondary">
                    {book.author}
                </Typography>
            </TableCell>
            <TableCell align="right">
                <Chip label={book.provider} size="small" variant="outlined" />
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: 'bold' }}>
                {book.price.toLocaleString('ru-RU', {
                    style: 'currency',
                    currency: 'RUB',
                })}
            </TableCell>
            <TableCell align="center">
                <Chip
                    label={book.stock}
                    size="small"
                    color={book.stock === 0 ? 'error' : book.stock < 5 ? 'warning' : 'success'}
                    variant="filled"
                />
            </TableCell>
            <TableCell align="center">
                <Box
                    sx={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        gap: 1,
                    }}
                >
                    <IconButton
                        size="small"
                        onClick={() => handleQuantityChange(book.id, -1)}
                        disabled={qty === 0}
                    >
                        <Remove fontSize="small" />
                    </IconButton>
                    <TextField
                        size="small"
                        type="number"
                        value={qty}
                        onChange={(e) => handleDirectInput(book.id, e.target.value)}
                        slotProps={{
                            input: {
                                style: {
                                    textAlign: 'center',
                                    width: 40,
                                    padding: '4px 8px',
                                },
                            },
                        }}
                        variant="outlined"
                        sx={{
                            '& .MuiOutlinedInput-root': {
                                borderRadius: 1,
                            },
                        }}
                    />
                    <IconButton size="small" onClick={() => handleQuantityChange(book.id, 1)}>
                        <Add fontSize="small" />
                    </IconButton>
                </Box>
            </TableCell>
        </TableRow>
    );
};

export default BooksTableItem;
