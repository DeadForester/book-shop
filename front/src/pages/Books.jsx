import GoodsList from '../components/books-page/GoodsList.jsx';
import Search from '../components/books-page/Search.jsx';
import Snack from "../shared/components/Snack.jsx";

import { goods } from '../data/goods';
import {Container} from "@mui/material";
import {useMemo, useState} from "react";
import PaginationControls from "../shared/components/PaginationControls.jsx";

const ITEMS_PER_PAGE = 9;

const Books = () => {
    const [search, setSearch] = useState('');
    const [isSnackOpen, setSnackOpen] = useState(false);
    const [page, setPage] = useState(1);

    const filteredProducts = useMemo(() => {
        if (!search.trim()) return goods;

        return goods.filter(good =>
            good.name.toLowerCase().includes(search.toLowerCase()) ||
            good.author?.toLowerCase().includes(search.toLowerCase())
        );
    }, [search]);

    const totalPages = Math.ceil(filteredProducts.length / ITEMS_PER_PAGE);
    const currentItems = useMemo(() => {
        const start = (page - 1) * ITEMS_PER_PAGE;
        return filteredProducts.slice(start, start + ITEMS_PER_PAGE);
    }, [filteredProducts, page]);

    const handleChange = (e) => {
        setSearch( e.target.value);
        setPage(1);
    };

    const handlePageChange = (event, value) => {
        setPage(value);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    const handleItemAdded = () => setSnackOpen(true);

    return (
        <>
            <Container
                sx={{mt: '1rem'}}
            >
                <Search
                    value={search}
                    onChange={handleChange}
                />
                <GoodsList
                    goods={currentItems}
                    onItemAdded={handleItemAdded}
                />
            </Container>

            <Snack isOpen={isSnackOpen} onClose={() => setSnackOpen(false)} />

            {totalPages > 1 && (
                <PaginationControls
                    page={page}
                    totalPages={totalPages}
                    onPageChange={handlePageChange}
                />
            )}
        </>
    );
};

export default Books;