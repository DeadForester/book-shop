import {AppBar, IconButton, Toolbar, Badge, Link} from "@mui/material";
import {ShoppingBasket} from '@mui/icons-material';
import {useBasketContext} from "../../context/hooks/useBasketContext.js";

const Header = ({handleCart}) => {
    const {order} = useBasketContext();

    return (
        <AppBar position="static">
            <Toolbar>
                <Link
                    href="/"
                    underline="none"
                    sx={{ typography: 'h6', flexGrow: 1, color: 'inherit' }}
                >
                    Book shop
                </Link>
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