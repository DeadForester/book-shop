import { ShoppingBasket } from '@mui/icons-material';
import {
    Divider,
    Drawer,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    Typography,
} from '@mui/material';

import { useAppSelector } from '@/hooks/useAppSelector.ts';

import BasketItem from './BasketItem.tsx';

interface BasketProps {
    cartOpen: boolean;
    closeCart: () => void;
}

const Basket = ({ cartOpen, closeCart }: BasketProps) => {
    const { items } = useAppSelector((state) => state.cart);

    return (
        <Drawer anchor="right" open={cartOpen} onClose={closeCart}>
            <List sx={{ width: '400px' }}>
                <ListItem>
                    <ListItemIcon>
                        <ShoppingBasket />
                    </ListItemIcon>
                    <ListItemText primary="Корзина" />
                </ListItem>
                <Divider />
                {!items.length ? (
                    <ListItem>Корзина пуста!</ListItem>
                ) : (
                    <>
                        {items.map((item) => (
                            <BasketItem
                                id={item.id}
                                name={item.book.name}
                                price={item.book.price}
                                quantity={item.quantity}
                                key={item.id}
                            />
                        ))}
                        <Divider />
                        <ListItem>
                            <Typography>
                                Общая стоимость:{' '}
                                {items.reduce((acc, item) => {
                                    return acc + item.book.price * item.quantity;
                                }, 0)}{' '}
                                рублей.
                            </Typography>
                        </ListItem>
                    </>
                )}
            </List>
        </Drawer>
    );
};

export default Basket;
