import { Visibility, VisibilityOff } from '@mui/icons-material';
import { IconButton, InputAdornment, TextField } from '@mui/material';
import { useState } from 'react';

interface PasswordProps {
    /** Текущее значение пароля */
    password: string;
    /** Колбэк для обновления значения пароля */
    setPassword: (newPassword: string) => void;
    /** Текст ошибки валидации, если есть */
    error: string;
    /** Сбрасывает состояние ошибки при вводе */
    resetErrors: () => void;
    /** Блокирует поле на время запроса */
    loading: boolean;
    /** Текст лейбла над полем */
    label: string;
    /** data-testid для e2e-тестов */
    testId?: string;
}

const Password = ({
    password,
    setPassword,
    error,
    resetErrors,
    loading,
    label = 'Пароль',
    testId,
}: PasswordProps) => {
    const [showPassword, setShowPassword] = useState(false);

    return (
        <TextField
            fullWidth
            label={label}
            type={showPassword ? 'text' : 'password'}
            value={password}
            onChange={(e) => {
                setPassword(e.target.value);
                if (error) resetErrors();
            }}
            error={!!error}
            helperText={error}
            margin="normal"
            slotProps={{
                input: {
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
                },
                htmlInput: {
                    'data-testid': `${testId}`,
                },
            }}
            disabled={loading}
        />
    );
};

export default Password;
