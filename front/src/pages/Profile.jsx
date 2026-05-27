import { Box, Container, Typography, Avatar, Button, Grid, Chip } from '@mui/material';
import { Email, History, Dashboard, Person, LocationOn, CreditCard, Notifications } from '@mui/icons-material';
import { Link as RouterLink } from 'react-router-dom';
import InfoSection from "../components/profile-page/InfoSection.jsx";
import {mockUser} from "../data/user.js";
import DevPlaceholder from "../shared/components/DevPlaceholder.jsx";

export default function Profile({ user = null }) {
    const currentUser = user || mockUser;

    const avatarLetter = currentUser.email ? currentUser.email.charAt(0).toUpperCase() : 'U';

    const isAdmin = currentUser.isAdmin;
    const navTo = isAdmin ? '/dashboard' : '/orders';
    const navLabel = isAdmin ? 'Панель администратора' : 'История заказов';
    const NavIcon = isAdmin ? Dashboard : History;

    return (
        <Container maxWidth="md" sx={{ mt: 4, mb: 6 }}>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 3, mb: 4, flexWrap: 'wrap' }}>
                <Avatar
                    sx={{
                        width: 90,
                        height: 90,
                        fontSize: '2.5rem',
                        bgcolor: 'primary.main',
                        boxShadow: 3
                    }}
                >
                    {avatarLetter}
                </Avatar>
                <Box>
                    <Typography variant="h4" component="h1" fontWeight="700">
                        {currentUser.name || 'Пользователь'}
                    </Typography>
                    <Typography
                        variant="body1"
                        color="text.secondary"
                        sx={{ display: 'flex', alignItems: 'center', gap: 0.5, mt: 0.5 }}
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

            <Grid container spacing={3}>
                <Grid size={{ xs: 12, sm: 6 }}>
                    <InfoSection
                        title="Личные данные"
                        icon={<Person color="primary" />}
                        items={[
                            { label: 'Телефон', value: currentUser.phone },
                            { label: 'Дата регистрации', value: currentUser.joinDate }
                        ]}
                    />
                </Grid>

                <Grid size={{ xs: 12, sm: 6 }}>
                    <InfoSection
                        title="Адрес доставки"
                        icon={<LocationOn color="primary" />}
                        items={[
                            { label: 'Основной адрес', value: currentUser.address }
                        ]}
                    />
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