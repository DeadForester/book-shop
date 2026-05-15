import {IconButton, ListItem, Typography} from "@mui/material";
import {Close} from "@mui/icons-material";
import {useBasketContext} from "../../context/hooks/useBasketContext.js";

const BasketItem = ({ id, name, price, quantity}) => {
    const {removeFromOrder} = useBasketContext();

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
            <IconButton
                className='btn btn-primary'
                onClick={() => removeFromOrder(id)}
            >
                <Close/>
            </IconButton>
        </ListItem>
    );
};

export default BasketItem;