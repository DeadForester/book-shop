import GoodsList from '../components/GoodsList';
import Search from '../components/Search';
import Snack from "../shared/components/Snack.jsx";

import { goods } from '../data/goods';
import {Container} from "@mui/material";
import {useBasketContext} from "../context/hooks/useBasketContext.js";
import {useState} from "react";

const Books = () => {
    const {order, setOrder} = useBasketContext();
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

    const addToOrder = (goodsItem) => {
        let quantity = 1;

        const indexInOrder = order.findIndex(
            (item) => item.id === goodsItem.id
        );

        if (indexInOrder > -1) {
            quantity = order[indexInOrder].quantity + 1;

            setOrder(order.map((item) => {
                    if (item.id !== goodsItem.id) return item;

                    return {
                        id: item.id,
                        name: item.name,
                        price: item.price,
                        quantity,
                    };
                }),
            );
        } else {
            setOrder(
                [
                    ...order,
                    {
                        id: goodsItem.id,
                        name: goodsItem.name,
                        price: goodsItem.price,
                        quantity,
                    },
                ],
            );
        }

        setSnackOpen(true)
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
                    setOrder={addToOrder}
                />
            </Container>

            <Snack isOpen={isSnackOpen} onClose={() => setSnackOpen(false)} />
        </>
    );
};

export default Books;