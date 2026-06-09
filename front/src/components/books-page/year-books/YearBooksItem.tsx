import { TrendingUp } from '@mui/icons-material';
import { Box, Card, Tooltip, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

import { Book } from '@/shared/types/Book.ts';

interface YearBooksItem {
    book: Book;
}

const YearBooksItem = ({ book }: YearBooksItem) => {
    const navigate = useNavigate();

    return (
        <Card
            key={book.id}
            sx={{
                display: 'flex',
                alignItems: 'center',
                gap: 2,
                p: 1,
                cursor: 'pointer',
                transition: 'box-shadow 0.2s',
                '&:hover': { boxShadow: 4, backgroundColor: 'action.hover' },
            }}
            onClick={() => navigate(`/books/${book.id}`)}
        >
            <Box
                component="img"
                src={book.poster}
                alt={book.name}
                sx={{
                    width: 60,
                    height: 90,
                    objectFit: 'cover',
                    borderRadius: 1,
                    boxShadow: 1,
                }}
            />
            <Box sx={{ flex: 1, minWidth: 0 }}>
                <Typography variant="subtitle2" sx={{ fontWeight: 'bold' }} noWrap>
                    {book.name}
                </Typography>
                <Typography variant="caption" color="text.secondary" noWrap>
                    {book.author}
                </Typography>
            </Box>
            <Tooltip title="Популярно">
                <TrendingUp color="success" fontSize="small" />
            </Tooltip>
        </Card>
    );
};

export default YearBooksItem;
