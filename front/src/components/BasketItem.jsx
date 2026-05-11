import {IconButton, ListItem, Typography} from "@mui/material";
import {Close} from "@mui/icons-material";

const BasketItem = ({removeFormOrder, id, name, price, quantity}) => {
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
                onClick={() => removeFormOrder(id)}
            >
                <Close/>
            </IconButton>
        </ListItem>
    );
};

export default BasketItem;