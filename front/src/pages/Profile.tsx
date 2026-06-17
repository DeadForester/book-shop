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
import { Avatar, Box, Button, Chip, Container, Grid, Typography } from '@mui/material';
import { useEffect } from 'react';
import { Link as RouterLink } from 'react-router-dom';

import { useAppDispatch } from '@/hooks/useAppDispatch.ts';
import { useAppSelector } from '@/hooks/useAppSelector.ts';
import { fetchUser } from '@/store/reducers/auth/thunks/featchUserThunk.ts';

import InfoSection from '../components/profile-page/InfoSection.tsx';
import DevPlaceholder from '../shared/components/DevPlaceholder.tsx';
import Loader from '../shared/components/Loader.tsx';

export default function Profile() {
    const dispatch = useAppDispatch();

    const { currentUser, isUserLoading, userError } = useAppSelector((state) => state.auth);

    useEffect(() => {
        dispatch(fetchUser());
    }, [dispatch]);

    useEffect(() => {
        console.log(currentUser);
        console.log('User admin: ' + currentUser?.isAdmin);
    }, [currentUser]);

    useEffect(() => {
        if (userError) {
            console.error('Ошибка загрузки профиля:', userError);
        }
    }, [userError]);

    if (isUserLoading) {
        return <Loader />;
    }

    if (currentUser === null) {
        return <h1>{userError ?? 'Ошибка при загрузке профиля :('}</h1>;
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
                    <Typography variant="h4" component="h1" sx={{ fontWeight: 700 }}>
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
                        label={currentUser.isAdmin ? 'Администратор' : 'Покупатель'}
                        color={currentUser.isAdmin ? 'error' : 'success'}
                        size="small"
                        sx={{ mt: 1 }}
                    />
                </Box>
            </Box>

            <Button
                component={RouterLink}
                to={currentUser.isAdmin ? '/panel' : '/orders'}
                variant="contained"
                size="large"
                startIcon={currentUser.isAdmin ? <Dashboard /> : <History />}
                fullWidth
                sx={{ mb: 4, py: 1.5, fontSize: '1.1rem' }}
            >
                {currentUser.isAdmin ? 'Панель администратора' : 'История заказов'}
            </Button>

            {currentUser.isAdmin && (
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
                        fields={[
                            { label: 'Телефон', value: currentUser.phone ?? '+7 (9__) ___-__-__' },
                            {
                                label: 'Дата регистрации',
                                value: currentUser.joinDate?.toString() ?? 'dd.mm.yyyy',
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
