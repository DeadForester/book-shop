import { ShoppingBasket } from '@mui/icons-material';
import { AppBar, Badge, IconButton, Link, Toolbar } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';

import { useAppSelector } from '@/hooks/useAppSelector.ts';

import UserCircle from './UserCircle.tsx';

interface HeaderProps {
    handleCart: () => void;
}

const Header = ({ handleCart }: HeaderProps) => {
    const { items } = useAppSelector((state) => state.cart);

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
                    <Badge color="secondary" badgeContent={items.length}>
                        <ShoppingBasket />
                    </Badge>
                </IconButton>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
