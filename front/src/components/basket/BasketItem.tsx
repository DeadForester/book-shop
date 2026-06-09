import { Close } from '@mui/icons-material';
import { IconButton, ListItem, Typography } from '@mui/material';

import { useAppDispatch } from '@/hooks/useAppDispatch.ts';
import { removeFromCart } from '@/store/reducers/cart/cartSlice.ts';

interface BasketItemProps {
    id: string;
    name: string;
    price: number;
    quantity: number;
}

const BasketItem = ({ id, name, price, quantity }: BasketItemProps) => {
    const dispatch = useAppDispatch();

    return (
        <ListItem>
            <Typography
                variant="body1"
                component="span"
                sx={{
                    flexGrow: 1,
                }}
            >
                {name} {price}руб x{quantity}
            </Typography>
            <IconButton className="btn btn-primary" onClick={() => dispatch(removeFromCart(id))}>
                <Close />
            </IconButton>
        </ListItem>
    );
};

export default BasketItem;
