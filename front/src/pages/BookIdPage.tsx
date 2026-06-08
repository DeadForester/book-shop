import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { Box, Button, Chip, Container, Divider, Grid, Paper, Typography } from '@mui/material';
import { useMemo } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';

import { addToCart } from '@/store/reducers/cart/cartSlice.ts';

import ActionButtons from '../components/book-id-page/ActionButtons.tsx';
import BookImage from '../components/book-id-page/BookImage.tsx';
import BookRating from '../components/book-id-page/BookRating.tsx';
import { goods } from '../data/goods.ts';

const BookIdPage = () => {
    const dispatch = useDispatch();
    const params = useParams();
    const navigate = useNavigate();

    const book = useMemo(
        () => goods.find((item) => String(item.id) === String(params.id)),
        [params.id]
    );

    if (!book) {
        return (
            <Container sx={{ mt: 4, textAlign: 'center' }}>
                <Typography variant="h5" color="error">
                    Книга не найдена
                </Typography>
                <Button sx={{ mt: 2 }} onClick={() => navigate('/')}>
                    Вернуться в каталог
                </Button>
            </Container>
        );
    }

    const handleAddToCart = () => {
        dispatch(addToCart(book));
    };

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 1 }}>
            <Button startIcon={<ArrowBackIcon />} onClick={() => navigate(-1)} sx={{ mb: 3 }}>
                Назад в каталог
            </Button>

            <Paper elevation={0} sx={{ p: { xs: 2, md: 4 }, backgroundColor: 'background.paper' }}>
                <Grid container spacing={4}>
                    <BookImage name={book.name} poster={book.poster} />

                    <Grid size={{ xs: 12, md: 7 }}>
                        <Typography variant="h3" component="h1" gutterBottom>
                            {book.name}
                        </Typography>

                        <Typography variant="subtitle1" color="text.secondary" sx={{ mb: 1 }}>
                            Автор: {book.author || 'Не указан'}
                        </Typography>

                        <BookRating rating={0} reviewsCount={0} />

                        <Typography
                            variant="h4"
                            color="primary.main"
                            sx={{ fontWeight: 700, mb: 2 }}
                        >
                            {book.price.toLocaleString('ru-RU')} ₽
                        </Typography>

                        <Divider sx={{ my: 2 }} />

                        <Typography variant="body1" component="p" sx={{ lineHeight: 1.7 }}>
                            {book.description || 'Описание отсутствует.'}
                        </Typography>

                        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1, mt: 3 }}>
                            <Chip label={book.genre} variant="outlined" size="small" />
                        </Box>

                        <ActionButtons handleAddToCart={handleAddToCart} />
                    </Grid>
                </Grid>
            </Paper>
        </Container>
    );
};

export default BookIdPage;
