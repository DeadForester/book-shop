import { Grid, Typography } from '@mui/material';
import BooksItem from './BooksItem.jsx';

export default function BooksList({ books }) {
    // useEffect(() => {
    //     const fetchData = async () => {
    //         try {
    //             setBooksFor(await GetArrayByUrl('http://localhost:5257/books/get'));
    //         } catch (error) {
    //             console.error('Error fetching data:', error);
    //         }
    //     };
    //     fetchData();
    // }, []);

    if (books.length === 0) return <Typography>Загрузка...</Typography>;

    return (
        <Grid container spacing={2}>
            {books.slice(0, 8).map((book) => (
                <Grid size={{ sm: 12, md: 6 }} key={book.id}>
                    <BooksItem book={book} />
                </Grid>
            ))}
        </Grid>
    );
}
