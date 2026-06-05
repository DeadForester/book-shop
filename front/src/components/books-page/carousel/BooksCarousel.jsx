import { useCallback, useState } from 'react';
import useEmblaCarousel from 'embla-carousel-react';
import Autoplay from 'embla-carousel-autoplay';
import { Box, IconButton } from '@mui/material';
import { ChevronLeft, ChevronRight } from '@mui/icons-material';
import { goods } from '../../../data/goods.ts';
import CarouselItem from './CarouselItem.jsx';

export default function BooksCarousel() {
    const [books] = useState(goods.slice(21, 27));

    const [emblaRef, emblaApi] = useEmblaCarousel(
        { loop: true, align: 'start', containScroll: 'trimSnaps' },
        [Autoplay({ delay: 6000, stopOnInteraction: true })]
    );

    const scrollPrev = useCallback(() => emblaApi?.scrollPrev(), [emblaApi]);
    const scrollNext = useCallback(() => emblaApi?.scrollNext(), [emblaApi]);

    if (books.length === 0) return null;

    return (
        <Box sx={{ py: 4, backgroundColor: 'grey.50', position: 'relative' }}>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, px: { xs: 2, md: 4 } }}>
                <IconButton
                    onClick={scrollPrev}
                    sx={{
                        backgroundColor: 'background.paper',
                        boxShadow: 2,
                        '&:hover': { backgroundColor: 'primary.light', color: 'white' },
                    }}
                >
                    <ChevronLeft />
                </IconButton>

                <Box ref={emblaRef} sx={{ flex: 1, overflow: 'hidden' }}>
                    <Box sx={{ display: 'flex', ml: -1 }}>
                        {books.map((book) => (
                            <CarouselItem key={book.id} book={book} />
                        ))}
                    </Box>
                </Box>

                <IconButton
                    onClick={scrollNext}
                    sx={{
                        backgroundColor: 'background.paper',
                        boxShadow: 2,
                        '&:hover': { backgroundColor: 'primary.light', color: 'white' },
                    }}
                >
                    <ChevronRight />
                </IconButton>
            </Box>
        </Box>
    );
}
