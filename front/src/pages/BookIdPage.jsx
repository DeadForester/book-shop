import {useNavigate, useParams} from "react-router-dom";
import {useMemo} from "react";
import {goods} from "../data/goods.js";
import {Box, Button, Chip, Container, Divider, Grid, Paper, Typography} from "@mui/material";
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useBasketContext} from "../hooks/useBasketContext.js";
import BookImage from "../components/book-id-page/BookImage.jsx";
import BookRating from "../components/book-id-page/BookRating.jsx";
import ActionButtons from "../components/book-id-page/ActionButtons.jsx";

const BookIdPage = () => {
    const {addToOrder} = useBasketContext();
    const params = useParams();
    const navigate = useNavigate();

    const book = useMemo(() => goods.find(item => String(item.id) === String(params.id)), [params.id]);

    if (!book) {
        return (
            <Container sx={{ mt: 4, textAlign: 'center' }}>
                <Typography variant="h5" color="error">Книга не найдена</Typography>
                <Button sx={{ mt: 2 }} onClick={() => navigate('/')}>Вернуться в каталог</Button>
            </Container>
        );
    }

    const handleAddToCart = () => {
        addToOrder({ id: book.id, name: book.name, price: book.price })
    }

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 1 }}>
            <Button
                startIcon={<ArrowBackIcon />}
                onClick={() => navigate(-1)}
                sx={{ mb: 3 }}
            >
                Назад в каталог
            </Button>

            <Paper elevation={0} sx={{ p: { xs: 2, md: 4 }, bgcolor: 'background.paper' }}>
                <Grid container spacing={4}>
                    {/* Изображение */}
                    <BookImage name={book.name} poster={book.poster} />

                    <Grid size={{ xs: 12, md: 7 }}>
                        <Typography variant="h3" component="h1" gutterBottom>
                            {book.name}
                        </Typography>

                        <Typography variant="subtitle1" color="text.secondary" sx={{ mb: 1 }}>
                            Автор: {book.author || 'Не указан'}
                        </Typography>

                        {/* Рейтинг */}
                        <BookRating rating={book.rating} reviewsCount={book.reviewsCount} />

                        {/* Цена */}
                        <Typography variant="h4" color="primary.main" sx={{ fontWeight: 700, mb: 2 }}>
                            {book.price.toLocaleString('ru-RU')} ₽
                        </Typography>

                        <Divider sx={{ my: 2 }} />

                        {/* Описание */}
                        <Typography variant="body1" paragraph sx={{ lineHeight: 1.7 }}>
                            {book.description || 'Описание отсутствует.'}
                        </Typography>

                        {/* Метаданные */}
                        {book.genres && (
                            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1, mt: 3 }}>
                                {book.genres.map((genre) => (
                                    <Chip key={genre} label={genre} variant="outlined" size="small" />
                                ))}
                            </Box>
                        )}

                        {/* Кнопки действий */}
                        <ActionButtons handleAddToCart={handleAddToCart}/>
                    </Grid>
                </Grid>
            </Paper>
        </Container>
    );
};

export default BookIdPage;