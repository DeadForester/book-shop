import { Avatar, Box, Button, Chip, Container, Grid, Typography } from '@mui/material';
import {
    CreditCard,
    Dashboard,
    Email,
    History,
    LocationOn,
    Notifications,
    Person,
    ShoppingCart,
} from '@mui/icons-material';
import { Link as RouterLink } from 'react-router-dom';
import InfoSection from '../components/profile-page/InfoSection.jsx';
import { mockUser } from '../data/user.js';
import DevPlaceholder from '../shared/components/DevPlaceholder.jsx';
import { useFetching } from '../hooks/useFetching.js';
import UserService from '../API/UserService.js';
import Loader from '../shared/components/Loader.jsx';
import { useEffect, useState } from 'react';

export default function Profile() {
    const [currentUser, setCurrentUser] = useState(mockUser);

    const [getUser, isLoading, error] = useFetching(async () => {
        const response = await UserService.getUserById(localStorage.getItem('userId'));
        setCurrentUser(response.data);
    });

    useEffect(() => {
        void getUser();
    }, [getUser]);

    useEffect(() => {
        if (error) {
            console.error('Ошибка загрузки профиля:', error);
        }
    }, [error]);

    const isAdmin = mockUser.isAdmin;
    const navTo = isAdmin ? '/dashboard' : '/orders';
    const navLabel = isAdmin ? 'Панель администратора' : 'История заказов';
    const NavIcon = isAdmin ? Dashboard : History;

    if (isLoading){
        return <Loader />;
    }

    return (
        <Container maxWidth="md" sx={{ mt: 4, mb: 6 }}>
            <Box
                sx={{
                    display: 'flex',
                    alignItems: 'center',
                    gap: 3,
                    mb: 4,
                    flexWrap: 'wrap',
                }}
            >
                <Avatar
                    sx={{
                        width: 90,
                        height: 90,
                        fontSize: '2.5rem',
                        backgroundColor: 'primary.main',
                        boxShadow: 3,
                    }}
                >
                    {currentUser.email.charAt(0).toUpperCase()}
                </Avatar>
                <Box>
                    <Typography variant="h4" component="h1" fontWeight="700">
                        {currentUser.name ?? 'Пользователь'}
                    </Typography>
                    <Typography
                        variant="body1"
                        color="text.secondary"
                        sx={{
                            display: 'flex',
                            alignItems: 'center',
                            gap: 0.5,
                            mt: 0.5,
                        }}
                    >
                        <Email fontSize="small" />
                        {currentUser.email}
                    </Typography>
                    <Chip
                        label={isAdmin ? 'Администратор' : 'Покупатель'}
                        color={isAdmin ? 'error' : 'success'}
                        size="small"
                        sx={{ mt: 1 }}
                    />
                </Box>
            </Box>

            <Button
                component={RouterLink}
                to={navTo}
                variant="contained"
                size="large"
                startIcon={<NavIcon />}
                fullWidth
                sx={{ mb: 4, py: 1.5, fontSize: '1.1rem' }}
            >
                {navLabel}
            </Button>

            {isAdmin && (
                <Button
                    component={RouterLink}
                    to="/storageOrder"
                    variant="contained"
                    size="large"
                    startIcon={<ShoppingCart />}
                    fullWidth
                    sx={{ mb: 4, py: 1.5, fontSize: '1.1rem' }}
                >
                    Заказать книги на склад
                </Button>
            )}

            <Grid container spacing={3}>
                <Grid size={{ xs: 12, sm: 6 }}>
                    <InfoSection
                        title="Личные данные"
                        icon={<Person color="primary" />}
                        items={[
                            { label: 'Телефон', value: currentUser.phone ?? '+7 (9__) ___-__-__' },
                            {
                                label: 'Дата регистрации',
                                value: currentUser.joinDate ?? 'dd.mm.yyyy',
                            },
                        ]}
                    />
                </Grid>

                <Grid size={{ xs: 12, sm: 6 }}>
                    <DevPlaceholder title="Адрес доставки" icon={<LocationOn />} />
                </Grid>

                <Grid size={{ xs: 12, sm: 6 }}>
                    <DevPlaceholder title="Способы оплаты" icon={<CreditCard />} />
                </Grid>
                <Grid size={{ xs: 12, sm: 6 }}>
                    <DevPlaceholder title="Настройки уведомлений" icon={<Notifications />} />
                </Grid>
            </Grid>
        </Container>
    );
}
