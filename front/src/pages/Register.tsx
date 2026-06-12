import { Email, PersonAddAlt1 as RegisterIcon } from '@mui/icons-material';
import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Divider,
    InputAdornment,
    TextField,
    Typography,
} from '@mui/material';
import { SyntheticEvent, useState } from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';

import { useAppDispatch } from '@/hooks/useAppDispatch.ts';
import { useAppSelector } from '@/hooks/useAppSelector.ts';
import { CredentialsErrors } from '@/shared/types/CredentialsErrors.ts';
import { SnackBar } from '@/shared/types/SnackBar.ts';
import { registration } from '@/store/reducers/auth/thunks/registrationThunk.ts';

import Password from '../shared/components/Password.tsx';
import { validateCredentials } from '../utils/validateCredentials.ts';

const Register = () => {
    const navigate = useNavigate();
    const { isRegistrationLoading, registrationError } = useAppSelector((state) => state.auth);
    const dispatch = useAppDispatch();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [errors, setErrors] = useState<CredentialsErrors>({});
    const [snackbar, setSnackbar] = useState<SnackBar>({
        open: false,
        message: '',
        severity: 'success',
    });

    const validate = () => {
        const validationsErrors = validateCredentials(email, password, confirmPassword);

        setErrors(validationsErrors);
        return Object.keys(validationsErrors).length === 0;
    };

    const handleSubmit = async (e: SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!validate()) return;

        await dispatch(registration({ email, password }));

        if (registrationError) {
            setSnackbar({
                open: true,
                message: 'Произошла ошибка при регистрации.',
                severity: 'error',
            });
            console.error('Register: ' + registrationError);
            return;
        }

        setSnackbar({
            open: true,
            message: 'Регистрация успешна! Перенаправляем на вход...',
            severity: 'success',
        });

        setTimeout(() => navigate('/login', { replace: true }), 2000);
    };

    return (
        <Box
            sx={{
                minHeight: '100vh',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                backgroundColor: 'background.default',
                p: 2,
                background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
            }}
        >
            <Card
                elevation={10}
                sx={{
                    width: '100%',
                    maxWidth: 420,
                    borderRadius: 3,
                    animation: 'fadeIn 0.3s ease-in-out',
                    '@keyframes fadeIn': {
                        from: { opacity: 0, transform: 'translateY(-20px)' },
                        to: { opacity: 1, transform: 'translateY(0)' },
                    },
                }}
            >
                <CardContent sx={{ p: 4 }}>
                    {/* Заголовок */}
                    <Box sx={{ textAlign: 'center', mb: 3 }}>
                        <Box
                            sx={{
                                width: 64,
                                height: 64,
                                borderRadius: '50%',
                                backgroundColor: 'primary.main',
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                mx: 'auto',
                                mb: 2,
                            }}
                        >
                            <RegisterIcon sx={{ color: 'white', fontSize: 32 }} />
                        </Box>
                        <Typography variant="h5" sx={{ fontWeight: 600 }}>
                            Создать аккаунт
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Заполните данные для регистрации
                        </Typography>
                    </Box>

                    {snackbar.open && (
                        <Alert
                            severity={snackbar.severity}
                            sx={{ mb: 2 }}
                            onClose={() => setSnackbar({ ...snackbar, open: false })}
                        >
                            {snackbar.message}
                        </Alert>
                    )}

                    <Box component="form" onSubmit={handleSubmit} noValidate>
                        {/* Email */}
                        <TextField
                            fullWidth
                            label="Email"
                            type="email"
                            value={email}
                            onChange={(e) => {
                                setEmail(e.target.value);
                                if (errors.email) setErrors({ ...errors, email: '' });
                            }}
                            error={!!errors.email}
                            helperText={errors.email}
                            margin="normal"
                            slotProps={{
                                input: {
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            <Email color="action" />
                                        </InputAdornment>
                                    ),
                                },
                            }}
                            disabled={isRegistrationLoading}
                        />

                        <Password
                            password={password}
                            setPassword={setPassword}
                            error={errors.password ?? ''}
                            resetErrors={() => setErrors({ ...errors, password: '' })}
                            loading={isRegistrationLoading}
                            label="Пароль"
                        />

                        <Password
                            password={confirmPassword}
                            setPassword={setConfirmPassword}
                            error={errors.confirmPassword ?? ''}
                            resetErrors={() => setErrors({ ...errors, confirmPassword: '' })}
                            loading={isRegistrationLoading}
                            label={'Повтор пароля'}
                        />

                        <Button
                            fullWidth
                            type="submit"
                            variant="contained"
                            size="large"
                            disabled={isRegistrationLoading}
                            startIcon={
                                isRegistrationLoading ? (
                                    <CircularProgress size={20} color="inherit" />
                                ) : (
                                    <RegisterIcon />
                                )
                            }
                            sx={{
                                mt: 3,
                                mb: 2,
                                py: 1.5,
                                borderRadius: 2,
                                textTransform: 'none',
                                fontSize: '1rem',
                                fontWeight: 600,
                            }}
                        >
                            {isRegistrationLoading ? 'Регистрация...' : 'Зарегистрироваться'}
                        </Button>

                        <Divider sx={{ my: 2 }}>
                            <Typography variant="body2" color="text.secondary">
                                или
                            </Typography>
                        </Divider>

                        <Typography
                            variant="body2"
                            sx={{ textAlign: 'center' }}
                            color="text.secondary"
                        >
                            Уже есть аккаунт?{' '}
                            <Typography
                                component={RouterLink}
                                to="/login"
                                color="primary"
                                sx={{
                                    textDecoration: 'none',
                                    fontWeight: 600,
                                    '&:hover': { textDecoration: 'underline' },
                                }}
                            >
                                Войти
                            </Typography>
                        </Typography>
                    </Box>
                </CardContent>
            </Card>
        </Box>
    );
};

export default Register;
