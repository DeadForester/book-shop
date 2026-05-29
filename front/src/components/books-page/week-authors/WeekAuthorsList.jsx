import { Avatar, Box, Chip } from '@mui/material';
import { Person } from '@mui/icons-material';
import { goods } from '../../../data/goods.js';
import styles from './WeekAuthorsList.module.scss';

const WeekAuthorsList = () => {
    const authors = goods.map((book) => book.author).slice(0, 5);

    if (authors.length === 0) return null;

    console.log(authors);

    return (
        <Box className={styles.container}>
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
