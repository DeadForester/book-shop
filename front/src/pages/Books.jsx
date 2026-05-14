import GoodsList from '../components/GoodsList';
import Search from '../components/Search';
import Snack from "../shared/components/Snack.jsx";

import { goods } from '../data/goods';
import {Container} from "@mui/material";
import {useState} from "react";

const Books = () => {
    const [search, setSearch] = useState('');
    const [products, setProducts] = useState(goods);
    const [isSnackOpen, setSnackOpen] = useState(false);

    const handleChange = (e) => {
        if (!e.target.value) {
            setProducts(goods);
            setSearch('');
            return;
        }

        setSearch(e.target.value);
        setProducts(
            products.filter((good) =>
                good.name.toLowerCase().includes(e.target.value.toLowerCase())
            )
        );
    };
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
                    goods={products}
                />
            </Container>

            <Snack isOpen={isSnackOpen} onClose={() => setSnackOpen(false)} />
        </>
    );
};

export default Books;