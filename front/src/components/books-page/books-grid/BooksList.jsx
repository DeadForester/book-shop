import { Box, Grid, InputAdornment, TextField, Typography } from '@mui/material';
import BooksItem from './BooksItem.jsx';
import { useMemo, useState } from 'react';
import { Search } from '@mui/icons-material';
import PaginationControls from '../../../shared/components/PaginationControls.jsx';
import { goods } from '../../../data/goods.js';
import AutocompleteSelector from '../../../shared/components/AutocompleteSelector.jsx';

export default function BooksList() {
    const [books] = useState(goods);
    const [searchQuery, setSearchQuery] = useState('');
    const [selectedGenre, setSelectedGenre] = useState(null);

    const [page, setPage] = useState(1);

    const ITEMS_PER_PAGE = 6;

    const availableGenres = useMemo(() => {
        const genres = books.map((book) => book.genre).filter(Boolean);
        return [...new Set(genres)].sort();
    }, [books]);

    const filteredBooks = useMemo(() => {
        return books.filter((book) => {
            const matchesSearch =
                !searchQuery.trim() || book.name.toLowerCase().includes(searchQuery.toLowerCase());

            const matchesGenre = !selectedGenre || book.genre === selectedGenre;

            return matchesSearch && matchesGenre;
        });
    }, [books, searchQuery, selectedGenre]);

    const totalPages = Math.ceil(filteredBooks.length / ITEMS_PER_PAGE);
    const currentItems = useMemo(() => {
        const start = (page - 1) * ITEMS_PER_PAGE;
        return filteredBooks.slice(start, start + ITEMS_PER_PAGE);
    }, [filteredBooks, page]);

    const handlePageChange = (event, value) => {
        setPage(value);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    const handleSearchChange = (e) => {
        setSearchQuery(e.target.value);
        setPage(1);
    };

    const handleGenreChange = (event, newValue) => {
        setSelectedGenre(newValue);
        setPage(1);
    };

    const handleClearFilters = () => {
        setSearchQuery('');
        setSelectedGenre(null);
    };

    if (books.length === 0) {
        return (
            <Typography textAlign="center" py={4}>
                Загрузка книг...
            </Typography>
        );
    }

    return (
        <Box sx={{ width: '100%' }}>
            <Box
                sx={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: 2,
                    mb: 3,
                    p: 2,
                    bgcolor: 'background.paper',
                    borderRadius: 2,
                    boxShadow: 1,
                    alignItems: 'flex-end',
                }}
            >
                <TextField
                    label="Поиск по названию"
                    variant="outlined"
                    size="small"
                    value={searchQuery}
                    onChange={handleSearchChange}
                    placeholder="Введите название книги..."
                    sx={{
                        minWidth: { xs: '100%', sm: 240 },
                        flex: { xs: '1 1 100%', sm: '0 1 auto' },
                    }}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <Search color="action" />
                            </InputAdornment>
                        ),
                    }}
                />

                <AutocompleteSelector
                    value={selectedGenre}
                    onChange={handleGenreChange}
                    options={availableGenres}
                    label={'Жанр'}
                    placeholder={'Выберите жанр...'}
                />

                {(searchQuery || selectedGenre) && (
                    <Box
                        component="button"
                        onClick={handleClearFilters}
                        sx={{
                            border: 'none',
                            bgcolor: 'transparent',
                            color: 'primary.main',
                            fontWeight: 500,
                            cursor: 'pointer',
                            fontSize: '0.875rem',
                            textDecoration: 'underline',
                            alignSelf: 'center',
                            '&:hover': { color: 'primary.dark' },
                            px: 1,
                        }}
                    >
                        Сбросить
                    </Box>
                )}
            </Box>

            {(searchQuery || selectedGenre) && (
                <Box sx={{ mb: 2 }}>
                    <Typography variant="body2" color="text.secondary">
                        Найдено книг:{' '}
                        <Typography component="span" fontWeight="bold">
                            {filteredBooks.length}
                        </Typography>
                    </Typography>
                </Box>
            )}

            {filteredBooks.length === 0 ? (
                <Box sx={{ textAlign: 'center', py: 6 }}>
                    <Typography variant="h6" color="text.secondary" gutterBottom>
                        Ничего не найдено
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        Попробуйте изменить параметры поиска или сбросить фильтры
                    </Typography>
                </Box>
            ) : (
                <>
                    <Grid container spacing={2}>
                        {currentItems.map((book) => (
                            <Grid size={{ xs: 12, sm: 6 }} key={book.id}>
                                <BooksItem book={book} />
                            </Grid>
                        ))}
                    </Grid>

                    <PaginationControls
                        page={page}
                        totalPages={totalPages}
                        onPageChange={handlePageChange}
                    />
                </>
            )}
        </Box>
    );
}
