import { ArrowForward } from '@mui/icons-material';
import { Box, Button, Card, CardMedia, Chip, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

import { Book } from '@/shared/types/Book.ts';

interface CarouselItemProps {
    index: number;
    book: Book;
}

const CarouselItem = ({ index, book }: CarouselItemProps) => {
    const navigate = useNavigate();
    const backgroundColor = index % 2 === 0 ? '#ffcdd2' : '#e1bee7';

    return (
        <Box
            sx={{
                flex: { xs: '0 0 100%', sm: '0 0 50%' },
                minWidth: 0,
                pl: 1,
                boxSizing: 'border-box',
            }}
        >
            <Card
                sx={{
                    display: 'flex',
                    height: { xs: 'auto', md: 320 },
                    backgroundColor: backgroundColor,
                    transition: 'transform 0.2s',
                    '&:hover': { transform: 'translateY(-4px)', boxShadow: 6 },
                }}
            >
                <CardMedia
                    component="img"
                    image={book.poster}
                    alt={book.name}
                    sx={{
                        width: { xs: 160, sm: 220 },
                        height: 300,
                        flexShrink: 0,
                        minWidth: { xs: 120, sm: 180 },
                        objectFit: 'cover',
                        backgroundColor: 'white',
                        p: 1,
                        borderRadius: 1,
                        boxShadow: '0 1px 7px 2px #c7c9d3',
                        borderBottom: '1px solid #dcddde',
                        transition: 'transform 0.3s ease',
                        '&:hover': { transform: 'scale(1.04)' },
                    }}
                />
                <Box sx={{ display: 'flex', flexDirection: 'column', flex: 1, p: 2 }}>
                    <Typography variant="h6" gutterBottom sx={{ fontWeight: 'bold' }}>
                        {book.name}
                    </Typography>
                    <Typography variant="body2" color="text.secondary" gutterBottom>
                        {book.author}
                    </Typography>
                    <Chip
                        label={book.genre ?? 'Жанр'}
                        size="small"
                        color="info"
                        sx={{ mb: 1, alignSelf: 'flex-start' }}
                    />
                    <Typography
                        variant="body2"
                        sx={{
                            flex: 1,
                            overflow: 'hidden',
                            display: '-webkit-box',
                            WebkitLineClamp: 3,
                            WebkitBoxOrient: 'vertical',
                        }}
                    >
                        {book.description ?? 'Описание книги'}
                    </Typography>
                    <Button
                        variant="contained"
                        size="small"
                        endIcon={<ArrowForward />}
                        onClick={() => navigate(`/books/${book.id}`)}
                        sx={{ mt: 1, alignSelf: 'flex-start' }}
                    >
                        Подробнее
                    </Button>
                </Box>
            </Card>
        </Box>
    );
};

export default CarouselItem;
