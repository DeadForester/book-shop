import { Box, Grid, Typography } from '@mui/material';
import { ChangeEvent, useMemo, useState } from 'react';

import BooksFilters from '@/components/books-page/books-grid/BooksFilters.tsx';
import { goods } from '@/data/goods.ts';
import PaginationControls from '@/shared/components/PaginationControls.tsx';
import { Book } from '@/shared/types/Book.ts';

import BooksItem from './BooksItem.tsx';

export default function BooksList() {
    const [books] = useState(goods);

    const [page, setPage] = useState(1);

    const ITEMS_PER_PAGE = 6;

    const [filteredBooks, setFilteredBooks] = useState<Book[]>([]);

    const totalPages = Math.ceil(filteredBooks.length / ITEMS_PER_PAGE);
    const currentItems = useMemo(() => {
        const start = (page - 1) * ITEMS_PER_PAGE;
        return filteredBooks.slice(start, start + ITEMS_PER_PAGE);
    }, [filteredBooks, page]);

    const handlePageChange = (_event: ChangeEvent<unknown>, value: number) => {
        setPage(value);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    if (books.length === 0) {
        return <Typography sx={{ py: 4, textAlign: 'center' }}>Загрузка книг...</Typography>;
    }

    return (
        <Box sx={{ width: '100%' }}>
            <BooksFilters
                books={books}
                onPageReset={() => setPage(1)}
                onFilteredBooksChange={setFilteredBooks}
            />

            {filteredBooks.length === 0 ? (
                <Box sx={{ textAlign: 'center', py: 6 }}>
                    <Typography variant="h6" color="text.secondary" gutterBottom>
                        Ничего не найдено
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        Попробуйте изменить параметры поиска или сбросить фильтры
                    </Typography>
                </Box>
            ) : (
                <>
                    <Grid container spacing={2}>
                        {currentItems.map((book) => (
                            <Grid size={{ xs: 12, sm: 6 }} key={book.id}>
                                <BooksItem book={book} />
                            </Grid>
                        ))}
                    </Grid>

                    <PaginationControls
                        page={page}
                        totalPages={totalPages}
                        onPageChange={handlePageChange}
                    />
                </>
            )}
        </Box>
    );
}
