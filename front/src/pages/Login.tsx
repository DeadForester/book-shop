import { Email, Login as LoginIcon } from '@mui/icons-material';
import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    Checkbox,
    CircularProgress,
    Divider,
    FormControlLabel,
    InputAdornment,
    TextField,
    Typography,
} from '@mui/material';
import { SyntheticEvent, useState } from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';

import { useAppDispatch } from '@/hooks/useAppDispatch.ts';
import { useAppSelector } from '@/hooks/useAppSelector.ts';
import { usePageTitle } from '@/hooks/usePageTitle.ts';
import { CredentialsErrors } from '@/shared/types/CredentialsErrors.ts';
import { SnackBar } from '@/shared/types/SnackBar.ts';
import { login } from '@/store/reducers/auth/thunks/loginThunk.ts';

import Password from '../shared/components/Password.tsx';
import { validateCredentials } from '../utils/validateCredentials.ts';

const Login = () => {
    const { isLoginLoading } = useAppSelector((state) => state.auth);
    const dispatch = useAppDispatch();
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);

    const [errors, setErrors] = useState<CredentialsErrors>({});
    const [snackbar, setSnackbar] = useState<SnackBar>({
        open: false,
        message: '',
        severity: 'success',
    });

    const handleSubmit = async (e: SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();

        const validationsErrors = validateCredentials(email, password);

        if (validationsErrors && Object.keys(validationsErrors).length > 0) {
            setErrors(validationsErrors);
            return;
        }

        console.log(email, password, rememberMe);

        try {
            await dispatch(login({ email, password, rememberMe })).unwrap();

            setSnackbar({
                open: true,
                message: 'Успешный вход.',
                severity: 'success',
            });

            setTimeout(() => navigate('/', { replace: true }), 1500);
        } catch (e: unknown) {
            setSnackbar({
                open: true,
                message: 'Не верные данные пользователя.',
                severity: 'error',
            });
            console.error('Login: ' + e);
        }
    };

    usePageTitle('Прочитайка - Вход');

    return (
        <Box
            sx={{
                minHeight: '100vh',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                backgroundColor: 'background.default',
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
                    <Box sx={{ textAlign: 'center', mb: 3 }}>
                        <Typography variant="h5" sx={{ fontWeight: 600 }}>
                            Добро пожаловать
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Войдите в аккаунт для продолжения
                        </Typography>
                    </Box>

                    {snackbar.open && (
                        <Alert
                            severity={snackbar.severity}
                            sx={{ mb: 2 }}
                            onClose={() => setSnackbar({ ...snackbar, open: false })}
                            data-testid="login-result"
                        >
                            {snackbar.message}
                        </Alert>
                    )}

                    <Box component="form" onSubmit={handleSubmit} noValidate>
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
                                htmlInput: {
                                    'data-testid': 'login-field',
                                },
                            }}
                            disabled={isLoginLoading}
                        />

                        <Password
                            password={password}
                            setPassword={setPassword}
                            error={errors.password ?? ''}
                            resetErrors={() => setErrors({ ...errors, password: '' })}
                            loading={isLoginLoading}
                            label={'Пароль'}
                            testId="password-field"
                        />

                        <Box
                            sx={{
                                display: 'flex',
                                justifyContent: 'space-between',
                                alignItems: 'center',
                                my: 2,
                            }}
                        >
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        checked={rememberMe}
                                        onChange={(e) => setRememberMe(e.target.checked)}
                                        disabled={isLoginLoading}
                                    />
                                }
                                label="Запомнить меня"
                            />
                            <Typography
                                component={RouterLink}
                                to="/forgot-password"
                                variant="body2"
                                color="primary"
                                sx={{
                                    textDecoration: 'none',
                                    '&:hover': { textDecoration: 'underline' },
                                }}
                            >
                                Забыли пароль?
                            </Typography>
                        </Box>

                        <Button
                            fullWidth
                            type="submit"
                            variant="contained"
                            size="large"
                            disabled={isLoginLoading}
                            startIcon={
                                isLoginLoading ? (
                                    <CircularProgress size={20} color="inherit" />
                                ) : (
                                    <LoginIcon />
                                )
                            }
                            sx={{
                                mt: 2,
                                mb: 2,
                                py: 1.5,
                                borderRadius: 2,
                                textTransform: 'none',
                                fontSize: '1rem',
                                fontWeight: 600,
                            }}
                            data-testid="login-button"
                        >
                            {isLoginLoading ? 'Вход...' : 'Войти'}
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
                            Нет аккаунта?{' '}
                            <Typography
                                component={RouterLink}
                                to="/register"
                                color="primary"
                                sx={{
                                    textDecoration: 'none',
                                    fontWeight: 600,
                                    '&:hover': { textDecoration: 'underline' },
                                }}
                            >
                                Зарегистрироваться
                            </Typography>
                        </Typography>
                    </Box>
                </CardContent>
            </Card>
        </Box>
    );
};

export default Login;
