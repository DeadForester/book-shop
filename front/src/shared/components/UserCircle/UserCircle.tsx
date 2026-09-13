import { AccountCircle } from '@mui/icons-material';
import { Avatar, IconButton, Menu, MenuItem } from '@mui/material';
import { MouseEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { useAppDispatch } from '@/hooks/useAppDispatch.ts';
import { useAppSelector } from '@/hooks/useAppSelector.ts';
import { LOGIN_ROUTE, PROFILE_ROUTE, ROOT_ROUTE } from '@/shared/constants/route-paths.ts';
import { logout } from '@/store/reducers/auth/authSlice.ts';

const UserCircle = () => {
    const navigate = useNavigate();
    const { isAuth, currentUser, userError } = useAppSelector((state) => state.auth);

    const dispatch = useAppDispatch();

    const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);
    const open = Boolean(anchorEl);

    const handleMenuOpen = (event: MouseEvent<HTMLButtonElement>) => {
        if (!isAuth) {
            navigate(LOGIN_ROUTE);
        } else {
            setAnchorEl(event.currentTarget);
        }
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleProfile = () => {
        navigate(PROFILE_ROUTE);
        handleMenuClose();
    };

    const handleLogout = () => {
        dispatch(logout());
        handleMenuClose();
        navigate(ROOT_ROUTE);
    };

    if (currentUser === null && userError) {
        return <h1>{userError}</h1>;
    }

    return (
        <>
            <IconButton color="inherit" onClick={handleMenuOpen}>
                {isAuth ? (
                    <Avatar sx={{ width: 32, height: 32, backgroundColor: 'secondary.main' }}>
                        {currentUser?.name?.charAt(0).toUpperCase() ||
                            currentUser?.email?.charAt(0).toUpperCase() ||
                            'U'}
                    </Avatar>
                ) : (
                    <AccountCircle />
                )}
            </IconButton>

            <Menu
                anchorEl={anchorEl}
                open={open}
                onClose={handleMenuClose}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                transformOrigin={{ vertical: 'top', horizontal: 'right' }}
            >
                <MenuItem onClick={handleProfile}>Профиль</MenuItem>
                <MenuItem onClick={handleLogout}>Выйти</MenuItem>
            </Menu>
        </>
    );
};

export default UserCircle;
