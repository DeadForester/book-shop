import {IconButton, Avatar, Menu, MenuItem} from "@mui/material";
import {AccountCircle} from '@mui/icons-material';
import {useAuthContext} from "../../hooks/useAuthContext.js";
import {useNavigate} from "react-router-dom";
import {useState} from "react";

const UserCircle = () => {
    const {isAuth, setIsAuth} = useAuthContext();
    const navigate = useNavigate();
    const user = {};

    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);

    const handleMenuOpen = (event) => {
        if (!isAuth) {
            navigate('/login');
        } else {
            setAnchorEl(event.currentTarget);
        }
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleProfile = () => {
        navigate('/profile');
        handleMenuClose();
    };

    const handleLogout = () => {
        localStorage.setItem('auth', 'false');
        setIsAuth(false);
        handleMenuClose();
        navigate('/');
    };

    return (
        <>
            <IconButton color="inherit" onClick={handleMenuOpen}>
                {isAuth ? (
                    <Avatar sx={{ width: 32, height: 32, backgroundColor: 'secondary.main' }}>
                        {user.name?.charAt(0) || user.email?.charAt(0) || 'U'}
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