import { useState } from 'react';
import { goods } from '../../../data/goods.ts';
import { Stack } from '@mui/material';
import YearBooksItem from './YearBooksItem.jsx';

export default function YearBooksList() {
    const [booksPop] = useState(goods.slice(45, 51));

    if (booksPop.length === 0) return null;

    return (
        <Stack spacing={1}>
            {booksPop.map((book) => (
                <YearBooksItem key={book.id} book={book} />
            ))}
        </Stack>
    );
}
