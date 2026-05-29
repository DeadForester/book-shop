import { Box, Button, Card, CardMedia, Chip, Typography } from '@mui/material';
import { ArrowForward } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import styles from './CarouselItem.module.scss';

const CarouselItem = ({ book }) => {
    const isEven = book.id % 2 === 0;
    const navigate = useNavigate();

    return (
        <Box key={book.id} className={styles.slide}>
            <Card className={`${styles.card} ${isEven ? styles.even : styles.odd}`}>
                <CardMedia
                    component="img"
                    className={styles.media}
                    image={book.poster}
                    alt={book.name}
                />
                <Box className={styles.content}>
                    <Typography variant="h6" className={styles.title}>
                        {book.name}
                    </Typography>
                    <Typography variant="body2" className={styles.author}>
                        {book.author}
                    </Typography>

                    <Chip
                        label={book.genre ?? 'Жанр'}
                        size="small"
                        color="info"
                        className={styles.chip}
                    />

                    <Typography variant="body2" className={styles.description}>
                        {book.bookDescription ?? 'Описание книги'}
                    </Typography>

                    <Button
                        variant="contained"
                        size="small"
                        endIcon={<ArrowForward />}
                        onClick={() => navigate(`/books/${book.id}`)}
                        className={styles.button}
                    >
                        Подробнее
                    </Button>
                </Box>
            </Card>
        </Box>
    );
};

export default CarouselItem;
