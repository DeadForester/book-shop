import { ArrowDownward, ArrowUpward, Search } from '@mui/icons-material';
import {
    Autocomplete,
    Box,
    FormControl,
    FormLabel,
    InputAdornment,
    Stack,
    TextField,
    ToggleButton,
    ToggleButtonGroup,
    Typography,
} from '@mui/material';
import { ChangeEvent, MouseEvent, SyntheticEvent, useEffect, useMemo, useState } from 'react';

import useDebounce from '@/hooks/useDebounce.ts';
import { Book } from '@/shared/types/Book.ts';

interface BookFiltersProps {
    books: Book[];
    onPageReset: () => void;
    onFilteredBooksChange: (filtered: Book[]) => void;
}

const BooksFilters = ({ books, onPageReset, onFilteredBooksChange }: BookFiltersProps) => {
    const [searchQuery, setSearchQuery] = useState('');
    const [debouncedSearch, setDebouncedSearch] = useState('');
    const [selectedGenre, setSelectedGenre] = useState<string | null>(null);
    const [sortBy, setSortBy] = useState<string | null>(null);

    const availableGenres = useMemo(() => {
        const genres = books.map((book) => book.genre).filter(Boolean);
        console.log('genres', genres);
        return [...new Set(genres)].sort();
    }, [books]);

    const filteredBooks = useMemo(() => {
        let result = books.filter((book) => {
            const matchesSearch =
                !debouncedSearch.trim() ||
                book.name.toLowerCase().includes(debouncedSearch.toLowerCase());
            const matchesGenre = !selectedGenre || book.genre === selectedGenre;
            return matchesSearch && matchesGenre;
        });

        if (sortBy === 'price-asc') {
            result = [...result].sort((a, b) => (a.price || 0) - (b.price || 0));
        } else if (sortBy === 'price-desc') {
            result = [...result].sort((a, b) => (b.price || 0) - (a.price || 0));
        }

        return result;
    }, [books, debouncedSearch, selectedGenre, sortBy]);

    useEffect(() => {
        onFilteredBooksChange(filteredBooks);
    }, [filteredBooks, onFilteredBooksChange]);

    const handleSearchChange = (e: ChangeEvent<HTMLInputElement>) => {
        setSearchQuery(e.target.value);
        handleDebouncedSearchChange(e.target.value);
    };

    const handleGenreChange = (_event: SyntheticEvent, newValue: string | null) => {
        setSelectedGenre(newValue);
        onPageReset();
    };

    const handleSortChange = (_event: MouseEvent<HTMLElement>, newSort: string) => {
        if (newSort === sortBy) {
            setSortBy(null);
        } else {
            setSortBy(newSort);
        }
    };

    const handleClearFilters = () => {
        setSearchQuery('');
        setDebouncedSearch('');
        setSelectedGenre(null);
        setSortBy(null);
        onPageReset();
    };

    const handleDebouncedSearchChange = useDebounce((query: unknown) => {
        if (typeof query === 'string') setDebouncedSearch(query);
        onPageReset();
    }, 500);

    return (
        <>
            <Box
                sx={{
                    display: 'flex',
                    flexWrap: 'wrap',
                    gap: 2,
                    mb: 3,
                    p: 2,
                    backgroundColor: 'background.paper',
                    borderRadius: 2,
                    boxShadow: 1,
                    alignItems: 'center',
                    justifyContent: 'space-between',
                }}
            >
                <Stack
                    sx={{
                        maxWidth: 350,
                        flex: 1,
                        gap: 1,
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
                        slotProps={{
                            input: {
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <Search color="action" />
                                    </InputAdornment>
                                ),
                            },
                        }}
                    />

                    <Autocomplete
                        value={selectedGenre}
                        onChange={handleGenreChange}
                        options={availableGenres}
                        renderInput={(params) => (
                            <TextField {...params} label="Жанр" placeholder="Выберите жанр..." />
                        )}
                        sx={{
                            flexGrow: 1,
                            height: '100%',
                        }}
                    />
                </Stack>

                <FormControl sx={{ minWidth: { xs: '100%', sm: 180 } }}>
                    <FormLabel
                        sx={{
                            textAlign: 'center',
                            fontSize: '12px',
                            color: 'text.secondary',
                            mb: 0.5,
                        }}
                    >
                        Сортировка по цене
                    </FormLabel>
                    <ToggleButtonGroup
                        value={sortBy}
                        onChange={handleSortChange}
                        exclusive
                        size="small"
                        sx={{
                            '& .MuiToggleButton-root': {
                                py: 0.5,
                                px: 1,
                                border: '1px solid',
                                borderColor: 'divider',
                                '&.Mui-selected': {
                                    backgroundColor: 'primary.light',
                                    color: 'primary.contrastText',
                                    '&:hover': { backgroundColor: 'primary.main' },
                                },
                            },
                        }}
                    >
                        <ToggleButton value="price-asc" aria-label="по возрастанию">
                            <ArrowUpward fontSize="small" sx={{ mr: 0.5 }} />
                            <Typography variant="caption">Сначала дешёвые</Typography>
                        </ToggleButton>
                        <ToggleButton value="price-desc" aria-label="по убыванию">
                            <ArrowDownward fontSize="small" sx={{ mr: 0.5 }} />
                            <Typography variant="caption">Сначала дорогие</Typography>
                        </ToggleButton>
                    </ToggleButtonGroup>
                </FormControl>

                {(searchQuery || selectedGenre) && (
                    <Box
                        component="button"
                        onClick={handleClearFilters}
                        sx={{
                            border: 'none',
                            backgroundColor: 'transparent',
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
                        <Typography component="span" sx={{ fontWeight: 'bold' }}>
                            {filteredBooks.length}
                        </Typography>
                    </Typography>
                </Box>
            )}
        </>
    );
};

export default BooksFilters;
