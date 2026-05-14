import {AppBar, IconButton, Toolbar, Typography, Badge} from "@mui/material";
import {ShoppingBasket} from '@mui/icons-material';
import {useBasketContext} from "../../context/hooks/useBasketContext.js";

const Header = ({handleCart}) => {
    const {order} = useBasketContext();

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography
                    variant="h6"
                    component="span"
                    sx={{flexGrow: 1}}
                >
                    Book shop
                </Typography>
                <IconButton
                    color="inherit"
                    onClick={handleCart}
                >
                    <Badge
                        color="secondary"
                        badgeContent={order.length}
                    >
                        <ShoppingBasket/>
                    </Badge>
                </IconButton>
            </Toolbar>
        </AppBar>
    );
};

export default Header;