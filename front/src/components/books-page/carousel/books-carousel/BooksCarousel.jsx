import { useCallback, useEffect, useState } from 'react';
import useEmblaCarousel from 'embla-carousel-react';
import Autoplay from 'embla-carousel-autoplay';
import { Box, IconButton, Tooltip, Typography } from '@mui/material';
import { ChevronLeft, ChevronRight } from '@mui/icons-material';
import { goods } from '../../../../data/goods.js';
import CarouselItem from '../books-carousel-item/CarouselItem.jsx';
import styles from './BooksCarousel.module.scss';

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
        <Box className={styles.carousel}>
            <Box className={styles.wrapper}>
                <IconButton onClick={scrollPrev} className={styles.navButton}>
                    <ChevronLeft />
                </IconButton>

                <Box className={styles.container} ref={emblaRef}>
                    <Box sx={{ display: 'flex', ml: -1 }}>
                        {books.map((book) => (
                            <CarouselItem key={book.id} book={book} />
                        ))}
                    </Box>
                </Box>

                <IconButton onClick={scrollNext} className={styles.navButton}>
                    <ChevronRight />
                </IconButton>
            </Box>

            {/* Пагинация */}
            {totalPages > 1 && (
                <Box sx={{ display: 'flex', justifyContent: 'center', gap: 0.5, mt: 2 }}>
                    {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
                        <Tooltip title={`Страница ${page}`} key={page}>
                            <IconButton
                                onClick={() => scrollTo(page - 1)}
                                size="small"
                                className={`${styles.pageButton} ${currentPage === page ? styles.active : ''}`}
                            >
                                <Typography variant="caption">{page}</Typography>
                            </IconButton>
                        </Tooltip>
                    ))}
                </Box>
            )}
        </Box>
    );
}
