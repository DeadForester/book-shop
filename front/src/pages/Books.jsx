import { goods } from '../data/goods';
import { Box, Card, CardContent, Container, Divider, Grid, Stack, Typography } from '@mui/material';
import { Favorite, Person } from '@mui/icons-material';
import { useMemo, useState } from 'react';
import BooksCarousel from '../components/books-page/carousel/BooksCarousel.jsx';
import YearBooksList from '../components/books-page/year-books/YearBooksList.jsx';
import WeekAuthorsList from '../components/books-page/week-authors/WeekAuthorsList.jsx';
import BooksList from '../components/books-page/books-grid/BooksList.jsx';
import PaginationControls from '../shared/components/PaginationControls.jsx';

const Books = () => {
    const [search] = useState('');
    const [page, setPage] = useState(1);

    const ITEMS_PER_PAGE = 6;

    const filteredProducts = useMemo(() => {
        if (!search.trim()) return goods;

        return goods.filter(
            (good) =>
                good.name.toLowerCase().includes(search.toLowerCase()) ||
                good.author?.toLowerCase().includes(search.toLowerCase())
        );
    }, [search]);

    const totalPages = Math.ceil(filteredProducts.length / ITEMS_PER_PAGE);
    const currentItems = useMemo(() => {
        const start = (page - 1) * ITEMS_PER_PAGE;
        return filteredProducts.slice(start, start + ITEMS_PER_PAGE);
    }, [filteredProducts, page]);

    const handlePageChange = (event, value) => {
        setPage(value);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    return (
        <Box sx={{ bgcolor: 'background.default', minHeight: '100vh' }}>
            <Container maxWidth="xl">
                {/* 🔹 Карусель с пагинацией */}
                <BooksCarousel />

                <Box sx={{ py: 4 }}>
                    <Grid container spacing={4}>
                        {/* Левая колонка */}
                        <Grid size={{ xs: 12, md: 4 }}>
                            <Stack spacing={3}>
                                <Card variant="outlined" sx={{ height: 'auto', minHeight: 200 }}>
                                    <CardContent>
                                        <Typography variant="h6" fontWeight="bold" gutterBottom>
                                            <Person
                                                color="primary"
                                                sx={{ verticalAlign: 'middle', mr: 0.5 }}
                                            />
                                            Авторы недели
                                        </Typography>
                                        <Divider sx={{ mb: 2 }} />
                                        <WeekAuthorsList />
                                    </CardContent>
                                </Card>

                                <Card variant="outlined">
                                    <CardContent>
                                        <Typography variant="h6" fontWeight="bold" gutterBottom>
                                            <Favorite
                                                color="error"
                                                sx={{ verticalAlign: 'middle', mr: 0.5 }}
                                            />
                                            Популярное в этом году
                                        </Typography>
                                        <Divider sx={{ mb: 2 }} />
                                        <YearBooksList />
                                    </CardContent>
                                </Card>
                            </Stack>
                        </Grid>

                        {/* Правая колонка */}
                        <Grid size={{ xs: 12, md: 8 }}>
                            <Card variant="outlined">
                                <CardContent>
                                    <Box
                                        sx={{
                                            display: 'flex',
                                            alignItems: 'center',
                                            justifyContent: 'space-between',
                                            mb: 2,
                                        }}
                                    >
                                        <Typography variant="h6" fontWeight="bold">
                                            📚 Подборки книг
                                        </Typography>
                                        <Typography variant="caption" color="text.secondary">
                                            Все жанры
                                        </Typography>
                                    </Box>
                                    <Divider sx={{ mb: 2 }} />
                                    <BooksList books={currentItems} />

                                    {totalPages > 1 && (
                                        <PaginationControls
                                            page={page}
                                            totalPages={totalPages}
                                            onPageChange={handlePageChange}
                                        />
                                    )}
                                </CardContent>
                            </Card>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
        </Box>
    );
};

export default Books;
