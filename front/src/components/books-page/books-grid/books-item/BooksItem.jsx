import { Box, Card, CardContent, CardMedia, Chip, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import styles from './BooksItem.module.scss';

export default function BooksItem({ book }) {
    const navigate = useNavigate();

    const formatPrice = (price) =>
        price?.toLocaleString('ru-RU', { style: 'currency', currency: 'RUB' });

    return (
        <Card onClick={() => navigate(`/books/${book.id}`)} className={styles.card}>
            {/* Верхняя часть: изображение + контент */}
            <Box className={styles.topSection}>
                {/* Обложка книги */}
                <CardMedia
                    component="img"
                    image={book.poster}
                    alt={book.name}
                    className={styles.image}
                />

                {/* Текстовый контент */}
                <CardContent sx={{ flex: 1, p: 3, pt: 2, position: 'relative' }}>
                    <Typography variant="h6" component="div" className={styles.title}>
                        {book.name}
                    </Typography>

                    <Typography variant="body2" className={styles.author}>
                        {book.author ?? 'Неизвестный автор'}
                    </Typography>

                    {/* Жанр */}
                    <Chip
                        label={book.idGenreNavigation?.genreName || 'Жанр'}
                        size="small"
                        color="info"
                        variant="outlined"
                        className={styles.chip}
                    />

                    {/* Описание (обрезается до 4 строк) */}
                    <Typography variant="body2" className={styles.description}>
                        {book.description ?? 'Описание книги'}
                    </Typography>
                </CardContent>
            </Box>

            {/* Нижняя часть: цена */}
            <Box className={styles.footer}>
                <Typography variant="body1" className={styles.price}>
                    <Typography component="span" fontWeight="600">
                        Цена:{' '}
                    </Typography>
                    {formatPrice(book.price)}
                </Typography>
            </Box>
        </Card>
    );
}
