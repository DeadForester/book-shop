import { useCallback, useEffect, useState } from 'react';
import useEmblaCarousel from 'embla-carousel-react';
import Autoplay from 'embla-carousel-autoplay';
import { Box, IconButton, Tooltip, Typography } from '@mui/material';
import { ChevronLeft, ChevronRight } from '@mui/icons-material';
import { goods } from '../../../data/goods.js';
import CarouselItem from './CarouselItem.jsx';

export default function BooksCarousel() {
    const ITEMS_PER_PAGE = 2;
    const [books] = useState(goods.slice(0, 5));
    const [currentPage, setCurrentPage] = useState(1);

    const [emblaRef, emblaApi] = useEmblaCarousel(
        { loop: true, align: 'start', containScroll: 'trimSnaps' },
        [Autoplay({ delay: 6000, stopOnInteraction: true })]
    );

    const updatePagination = useCallback(() => {
        setCurrentPage((prev) => {
            const current = emblaApi?.selectedScrollSnap() + 1;
            return current !== undefined && current !== prev ? current : prev;
        });
    }, [emblaApi]);

    useEffect(() => {
        if (!emblaApi) return;

        emblaApi.on('select', updatePagination);
        emblaApi.on('reInit', updatePagination);

        return () => {
            emblaApi.off('select', updatePagination);
            emblaApi.off('reInit', updatePagination);
        };
    }, [emblaApi, updatePagination]);

    // Навигация
    const scrollPrev = useCallback(() => emblaApi?.scrollPrev(), [emblaApi]);
    const scrollNext = useCallback(() => emblaApi?.scrollNext(), [emblaApi]);
    const scrollTo = useCallback((index) => emblaApi?.scrollTo(index), [emblaApi]);

    const totalPages = Math.ceil(books.length / ITEMS_PER_PAGE);

    if (books.length === 0) return <></>;

    return (
        <Box sx={{ py: 4, bgcolor: 'grey.50', position: 'relative' }}>
            <Box
                sx={{
                    display: 'flex',
                    alignItems: 'center',
                    gap: 1,
                    px: { xs: 2, md: 4 },
                }}
            >
                <IconButton
                    onClick={scrollPrev}
                    sx={{
                        bgcolor: 'background.paper',
                        boxShadow: 2,
                        '&:hover': { bgcolor: 'primary.light', color: 'white' },
                    }}
                >
                    <ChevronLeft />
                </IconButton>

                <Box className="embla" sx={{ flex: 1, overflow: 'hidden' }} ref={emblaRef}>
                    <Box className="embla__container" sx={{ display: 'flex', ml: -1 }}>
                        {books.map((book) => (
                            <CarouselItem key={book.id} book={book} />
                        ))}
                    </Box>
                </Box>

                <IconButton
                    onClick={scrollNext}
                    sx={{
                        bgcolor: 'background.paper',
                        boxShadow: 2,
                        '&:hover': { bgcolor: 'primary.light', color: 'white' },
                    }}
                >
                    <ChevronRight />
                </IconButton>
            </Box>

            {/* Пагинация в стиле PaginationControls */}
            {totalPages > 1 && (
                <Box sx={{ display: 'flex', justifyContent: 'center', gap: 0.5, mt: 2 }}>
                    {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
                        <Tooltip title={`Страница ${page}`} key={page}>
                            <IconButton
                                onClick={() => scrollTo(page - 1)}
                                size="small"
                                sx={{
                                    width: 32,
                                    height: 32,
                                    minWidth: 32,
                                    bgcolor: currentPage === page ? 'primary.main' : 'grey.200',
                                    color: currentPage === page ? 'white' : 'text.primary',
                                    '&:hover': {
                                        bgcolor: currentPage === page ? 'primary.dark' : 'grey.300',
                                    },
                                }}
                            >
                                <Typography
                                    variant="caption"
                                    fontWeight={currentPage === page ? 'bold' : 'normal'}
                                >
                                    {page}
                                </Typography>
                            </IconButton>
                        </Tooltip>
                    ))}
                </Box>
            )}
        </Box>
    );
}
