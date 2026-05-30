import { useState } from 'react';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
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
import { Email, PersonAddAlt1 as RegisterIcon } from '@mui/icons-material';
import Password from '../shared/components/Password.jsx';
import { validateCredentials } from '../utils/validateCredentials.js';

const Register = () => {
    const navigate = useNavigate();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);
    const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

    const validate = () => {
        const validationsErrors = validateCredentials(email, password, confirmPassword);

        setErrors(validationsErrors);
        return Object.keys(validationsErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validate()) return;

        setLoading(true);

        await new Promise((resolve) => setTimeout(resolve, 1200));

        setSnackbar({
            open: true,
            message: 'Регистрация успешна! Перенаправляем на вход...',
            severity: 'success',
        });
        setLoading(false);

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
                        <Typography variant="h5" fontWeight={600}>
                            Создать аккаунт
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            Заполните данные для регистрации
                        </Typography>
                    </Box>

                    {/* Уведомление */}
                    {snackbar.open && (
                        <Alert
                            severity={snackbar.severity}
                            sx={{ mb: 2 }}
                            onClose={() => setSnackbar({ ...snackbar, open: false })}
                        >
                            {snackbar.message}
                        </Alert>
                    )}

                    {/* Форма */}
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
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <Email color="action" />
                                    </InputAdornment>
                                ),
                            }}
                            disabled={loading}
                        />

                        <Password
                            password={password}
                            setPassword={setPassword}
                            error={errors.password}
                            resetErrors={() => setErrors({ ...errors, password: '' })}
                            loading={loading}
                        />

                        <Password
                            password={confirmPassword}
                            setPassword={setConfirmPassword}
                            error={errors.confirmPassword}
                            resetErrors={() => setErrors({ ...errors, confirmPassword: '' })}
                            loading={loading}
                            label={'Повтор пароля'}
                        />

                        {/* Кнопка регистрации */}
                        <Button
                            fullWidth
                            type="submit"
                            variant="contained"
                            size="large"
                            disabled={loading}
                            startIcon={
                                loading ? (
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
                            {loading ? 'Регистрация...' : 'Зарегистрироваться'}
                        </Button>

                        <Divider sx={{ my: 2 }}>
                            <Typography variant="body2" color="text.secondary">
                                или
                            </Typography>
                        </Divider>

                        {/* Ссылка на вход */}
                        <Typography variant="body2" textAlign="center" color="text.secondary">
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
