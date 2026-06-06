import { AppBar, Badge, IconButton, Link, Toolbar } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { ShoppingBasket } from '@mui/icons-material';
import { useBasketContext } from '../../hooks/useBasketContext.ts';
import UserCircle from './UserCircle.jsx';

const Header = ({ handleCart }) => {
    const { order } = useBasketContext();

    return (
        <AppBar position="static">
            <Toolbar>
                <Link
                    component={RouterLink}
                    to="/"
                    underline="none"
                    sx={{ typography: 'h6', flexGrow: 1, color: 'inherit' }}
                >
                    Book shop
                </Link>

                <UserCircle />

                <IconButton color="inherit" onClick={handleCart}>
                    <Badge color="secondary" badgeContent={order.length}>
                        <ShoppingBasket />
                    </Badge>
                </IconButton>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
