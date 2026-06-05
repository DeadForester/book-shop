import { Avatar, Box, Chip } from '@mui/material';
import { Person } from '@mui/icons-material';
import { goods } from '../../../data/goods.ts';

const WeekAuthorsList = () => {
    const authors = [...new Set(goods.map((book) => book.author).filter(Boolean))].slice(31, 36);
    if (authors.length === 0) return null;

    return (
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1, width: '100%' }}>
            {authors.map((author, idx) => (
                <Chip
                    key={`${author}-${idx}`}
                    avatar={
                        <Avatar sx={{ backgroundColor: 'primary.light' }}>
                            <Person fontSize="small" />
                        </Avatar>
                    }
                    label={author}
                    variant="outlined"
                    size="small"
                />
            ))}
        </Box>
    );
};

export default WeekAuthorsList;
