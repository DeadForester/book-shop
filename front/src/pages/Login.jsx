import {useState} from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import {
    Box,
    Card,
    CardContent,
    TextField,
    Button,
    Typography,
    IconButton,
    InputAdornment,
    FormControlLabel,
    Checkbox,
    Divider,
    Alert,
    CircularProgress,
} from '@mui/material';
import {
    LockOutlined,
    Visibility,
    VisibilityOff,
    Email,
    Login as LoginIcon,
} from '@mui/icons-material';
import { useAuthContext } from "../hooks/useAuthContext.js";
import {validateCredentials} from "../utils/validateCredentials.js";

const Login = () => {
    const { setIsAuth } = useAuthContext();
    const navigate = useNavigate();

    const [email, setEmail] = useState(localStorage.getItem('rememberedEmail') ?? '');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(!!localStorage.getItem('rememberedEmail'));
    const [showPassword, setShowPassword] = useState(false);

    const [errors, setErrors] = useState({});
    const [loading, setLoading] = useState(false);
    const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateCredentials(email, password, setErrors)) return;

        setLoading(true);

        setIsAuth(true);
        localStorage.setItem('auth', 'true');

        if (rememberMe) {
            localStorage.setItem('rememberedEmail', email);
        }

        setSnackbar({
            open: true,
            message: 'Успешный вход!',
            severity: 'success'
        });

        setTimeout(() => navigate('/'), 1500);
    };

    return (
        <Box
            sx={{
                minHeight: '100vh',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                bgcolor: 'background.default',
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
                        <Typography variant="h5" fontWeight={600}>
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
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <Email color="action" />
                                    </InputAdornment>
                                ),
                            }}
                            disabled={loading}
                        />

                        <TextField
                            fullWidth
                            label="Пароль"
                            type={showPassword ? 'text' : 'password'}
                            value={password}
                            onChange={(e) => {
                                setPassword(e.target.value);
                                if (errors.password) setErrors({ ...errors, password: '' });
                            }}
                            error={!!errors.password}
                            helperText={errors.password}
                            margin="normal"
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <LockOutlined color="action" />
                                    </InputAdornment>
                                ),
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton
                                            onClick={() => setShowPassword(!showPassword)}
                                            edge="end"
                                            disabled={loading}
                                        >
                                            {showPassword ? <VisibilityOff /> : <Visibility />}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                            disabled={loading}
                        />

                        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', my: 2 }}>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        checked={rememberMe}
                                        onChange={(e) => setRememberMe(e.target.checked)}
                                        disabled={loading}
                                    />
                                }
                                label="Запомнить меня"
                            />
                            <Typography
                                component={RouterLink}
                                to="/forgot-password"
                                variant="body2"
                                color="primary"
                                sx={{ textDecoration: 'none', '&:hover': { textDecoration: 'underline' } }}
                            >
                                Забыли пароль?
                            </Typography>
                        </Box>

                        <Button
                            fullWidth
                            type="submit"
                            variant="contained"
                            size="large"
                            disabled={loading}
                            startIcon={loading ? <CircularProgress size={20} color="inherit" /> : <LoginIcon />}
                            sx={{
                                mt: 2,
                                mb: 2,
                                py: 1.5,
                                borderRadius: 2,
                                textTransform: 'none',
                                fontSize: '1rem',
                                fontWeight: 600,
                            }}
                        >
                            {loading ? 'Вход...' : 'Войти'}
                        </Button>

                        <Divider sx={{ my: 2 }}>
                            <Typography variant="body2" color="text.secondary">или</Typography>
                        </Divider>

                        <Typography variant="body2" textAlign="center" color="text.secondary">
                            Нет аккаунта?{' '}
                            <Typography
                                component={RouterLink}
                                to="/register"
                                color="primary"
                                sx={{ textDecoration: 'none', fontWeight: 600, '&:hover': { textDecoration: 'underline' } }}
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