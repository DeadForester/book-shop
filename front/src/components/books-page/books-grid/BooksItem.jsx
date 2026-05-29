import { Box, Card, CardContent, CardMedia, Chip, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function BooksItem({ book }) {
    const navigate = useNavigate();

    const formatPrice = (price) =>
        price?.toLocaleString('ru-RU', { style: 'currency', currency: 'RUB' });

    return (
        <Card
            onClick={() => navigate(`/books/${book.id}`)}
            sx={{
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                cursor: 'pointer',
                transition: 'transform 0.2s, box-shadow 0.2s',
                '&:hover': {
                    transform: 'translateY(-4px)',
                    boxShadow: 6,
                },
                position: 'relative',
                '&::after': {
                    content: '""',
                    position: 'absolute',
                    right: 12,
                    top: 17,
                    width: 4,
                    height: 4,
                    borderRadius: '50%',
                    bgcolor: '#8f98a9',
                    boxShadow: '0 -6px 0 0 #aaaebc, 0 6px 0 0 #aaaebc',
                },
            }}
            elevation={2}
        >
            {/* Верхняя часть: изображение + контент */}
            <Box sx={{ display: 'flex', borderBottom: '1px solid', borderColor: 'divider' }}>
                {/* Обложка книги */}
                <CardMedia
                    component="img"
                    image={book.poster}
                    alt={book.name}
                    sx={{
                        width: 160,
                        height: 220,
                        objectFit: 'cover',
                        mt: -4,
                        mb: 2.5,
                        ml: 0,
                        borderRadius: 1,
                        boxShadow: '0 1px 7px 2px #c7c9d3',
                        borderBottom: '1px solid #dcddde',
                        transition: 'transform 0.3s ease',
                        '&:hover': {
                            transform: 'scale(1.04)',
                        },
                    }}
                />

                {/* Текстовый контент */}
                <CardContent sx={{ flex: 1, p: 3, pt: 2, position: 'relative' }}>
                    <Typography
                        variant="h6"
                        component="div"
                        fontWeight="500"
                        sx={{
                            overflow: 'hidden',
                            textOverflow: 'ellipsis',
                            mb: 0.5,
                        }}
                    >
                        {book.name}
                    </Typography>

                    <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{
                            fontSize: '13px',
                            overflow: 'hidden',
                            textOverflow: 'ellipsis',
                            whiteSpace: 'nowrap',
                            mb: 1,
                        }}
                    >
                        {book.author ?? 'Неизвестный автор'}
                    </Typography>

                    {/* Жанр */}
                    <Chip
                        label={book.idGenreNavigation?.genreName || 'Жанр'}
                        size="small"
                        color="info"
                        variant="outlined"
                        sx={{
                            mb: 1.5,
                            alignSelf: 'flex-start',
                            height: 24,
                            fontSize: '12px',
                        }}
                    />

                    {/* Описание (обрезается до 4 строк) */}
                    <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{
                            fontSize: '13px',
                            lineHeight: 1.6,
                            display: '-webkit-box',
                            WebkitLineClamp: 4,
                            WebkitBoxOrient: 'vertical',
                            overflow: 'hidden',
                            mt: 1,
                        }}
                    >
                        {book.description ?? 'Описание книги'}
                    </Typography>
                </CardContent>
            </Box>

            {/* Нижняя часть: цена */}
            <Box sx={{ px: 2, py: 1.5, mt: 'auto' }}>
                <Typography
                    variant="body1"
                    sx={{
                        fontSize: '13pt',
                        fontWeight: 400,
                        color: 'text.primary',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        whiteSpace: 'nowrap',
                        '& span': { fontWeight: 600 },
                    }}
                >
                    <Typography component="span" fontWeight="600">
                        Цена:{' '}
                    </Typography>
                    {formatPrice(book.price)}
                </Typography>
            </Box>
        </Card>
    );
}
