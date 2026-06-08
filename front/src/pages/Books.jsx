import { Box, Card, CardContent, Container, Divider, Grid, Stack, Typography } from '@mui/material';
import { Favorite, Person } from '@mui/icons-material';
import {
    BooksCarousel,
    BooksList,
    WeekAuthorsList,
    YearBooksList,
} from '../components/books-page/index.ts';

const Books = () => {
    return (
        <Box sx={{ backgroundColor: 'background.default', minHeight: '100vh' }}>
            <Container maxWidth="xl">
                <BooksCarousel />

                <Box sx={{ py: 4 }}>
                    <Grid container spacing={4}>
                        <Grid size={{ xs: 12, md: 4 }}>
                            <Stack spacing={3}>
                                <Card variant="outlined" sx={{ height: 'auto', minHeight: 200 }}>
                                    <CardContent>
                                        <Typography variant="h6" fontWeight="bold" gutterBottom>
                                            <Person
                                                color="primary"
                                                sx={{ verticalAlign: 'middle', mr: 0.5 }}
                                            />
                                            Авторы недели
                                        </Typography>
                                        <Divider sx={{ mb: 2 }} />
                                        <WeekAuthorsList />
                                    </CardContent>
                                </Card>

                                <Card variant="outlined">
                                    <CardContent>
                                        <Typography variant="h6" fontWeight="bold" gutterBottom>
                                            <Favorite
                                                color="error"
                                                sx={{ verticalAlign: 'middle', mr: 0.5 }}
                                            />
                                            Популярное в этом году
                                        </Typography>
                                        <Divider sx={{ mb: 2 }} />
                                        <YearBooksList />
                                    </CardContent>
                                </Card>
                            </Stack>
                        </Grid>

                        <Grid size={{ xs: 12, md: 8 }}>
                            <Card variant="outlined">
                                <CardContent>
                                    <BooksList />
                                </CardContent>
                            </Card>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
        </Box>
    );
};

export default Books;
