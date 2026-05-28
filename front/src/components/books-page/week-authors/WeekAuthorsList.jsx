import { Avatar, Box, Chip } from '@mui/material';
import { Person } from '@mui/icons-material';
import { goods } from '../../../data/goods.js';

const WeekAuthorsList = () => {
    const authors = goods.map((book) => book.author).slice(0, 5);

    if (authors.length === 0) return null;

    console.log(authors);

    return (
        <Box
            sx={{
                display: 'flex',
                flexWrap: 'wrap',
                gap: '10px',
                width: '100%',
                minHeight: 'auto',
            }}
        >
            {authors.map((author, idx) => (
                <Chip
                    key={idx}
                    avatar={
                        <Avatar>
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
