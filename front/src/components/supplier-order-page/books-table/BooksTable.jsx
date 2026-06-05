import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
} from '@mui/material';
import { useMemo } from 'react';
import { goods } from '../../../data/goods.ts';
import BooksTableItem from './BooksTableItem.jsx';

const BooksTable = ({ search, selectedSupplier, orderItems, setOrderItems }) => {
    const filteredBooks = useMemo(() => {
        return goods.filter((book) => {
            const matchesSearch =
                !search.trim() ||
                book.name.toLowerCase().includes(search.toLowerCase()) ||
                book.author.toLowerCase().includes(search.toLowerCase());
            const matchesSupplier = !selectedSupplier || book.provider === selectedSupplier;
            return matchesSearch && matchesSupplier;
        });
    }, [search, selectedSupplier]);

    return (
        <TableContainer component={Paper} variant="outlined" sx={{ mb: 3, overflowX: 'auto' }}>
            <Table size="small">
                <TableHead sx={{ backgroundColor: 'grey.50' }}>
                    <TableRow>
                        <TableCell>Книга</TableCell>
                        <TableCell align="right">Поставщик</TableCell>
                        <TableCell align="right">Цена</TableCell>
                        <TableCell align="center">Остаток</TableCell>
                        <TableCell align="center" sx={{ minWidth: 140 }}>
                            К заказу
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {filteredBooks.length === 0 ? (
                        <TableRow>
                            <TableCell
                                colSpan={5}
                                sx={{ textAlign: 'center', py: 4, color: 'text.secondary' }}
                            >
                                Ничего не найдено. Измените параметры поиска.
                            </TableCell>
                        </TableRow>
                    ) : (
                        filteredBooks.map((book) => (
                            <BooksTableItem
                                key={book.id}
                                book={book}
                                orderItems={orderItems}
                                setOrderItems={setOrderItems}
                            />
                        ))
                    )}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default BooksTable;
