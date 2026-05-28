import { useState } from 'react';
import { goods } from '../../../data/goods.js';
import { Stack } from '@mui/material';
import YearBooksItem from './YearBooksItem.jsx';

export default function YearBooksList() {
    const [booksPop] = useState(goods.slice(0, 5));

    // useEffect(() => {
    //     const fetchData = async () => {
    //         try {
    //             const books = await GetArrayByUrl('http://localhost:5257/books/popular');
    //             setBooksPop(books.slice(0, 5));
    //         } catch (error) {
    //             console.error('Error fetching data:', error);
    //         }
    //     };
    //     fetchData();
    // }, []);

    if (booksPop.length === 0) return null;

    return (
        <Stack spacing={1}>
            {booksPop.map((book) => (
                <YearBooksItem key={book.id} book={book} />
            ))}
        </Stack>
    );
}
