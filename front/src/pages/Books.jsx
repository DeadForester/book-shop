import { goods } from '../data/goods';
import { Box, Card, CardContent, Container, Divider, Grid, Stack, Typography } from '@mui/material';
import { Favorite, Person } from '@mui/icons-material';
import { useMemo, useState } from 'react';
import { BooksCarousel, BooksList, WeekAuthorsList, YearBooksList } from '../components/books-page';
import PaginationControls from '../shared/components/PaginationControls.jsx';
import styles from '../styles/pages/Books.module.scss';

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
        <Box className={styles.page}>
            <Container maxWidth="xl">
                {/* 🔹 Карусель с пагинацией */}
                <BooksCarousel />

                <Box className={styles.section}>
                    <Grid container spacing={4}>
                        {/* Левая колонка */}
                        <Grid size={{ xs: 12, md: 4 }}>
                            <Stack spacing={3}>
                                <Card variant="outlined" className={styles.card}>
                                    <CardContent>
                                        <Typography variant="h6" className={styles.cardTitle}>
                                            <Person
                                                color="primary"
                                                sx={{ verticalAlign: 'middle', mr: 0.5 }}
                                            />
                                            Авторы недели
                                        </Typography>
                                        <Divider className={styles.divider} />
                                        <WeekAuthorsList />
                                    </CardContent>
                                </Card>

                                <Card variant="outlined">
                                    <CardContent>
                                        <Typography variant="h6" className={styles.cardTitle}>
                                            <Favorite
                                                color="error"
                                                sx={{ verticalAlign: 'middle', mr: 0.5 }}
                                            />
                                            Популярное в этом году
                                        </Typography>
                                        <Divider className={styles.divider} />
                                        <YearBooksList />
                                    </CardContent>
                                </Card>
                            </Stack>
                        </Grid>

                        {/* Правая колонка */}
                        <Grid size={{ xs: 12, md: 8 }}>
                            <Card variant="outlined">
                                <CardContent>
                                    <Box className={styles.header}>
                                        <Typography variant="h6" fontWeight="bold">
                                            📚 Подборки книг
                                        </Typography>
                                        <Typography variant="caption" color="text.secondary">
                                            Все жанры
                                        </Typography>
                                    </Box>
                                    <Divider className={styles.divider} />
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
