import { Visibility, VisibilityOff } from '@mui/icons-material';
import { IconButton, InputAdornment, TextField } from '@mui/material';
import { useState } from 'react';

interface PasswordProps {
    password: string;
    setPassword: (newPassword: string) => void;
    error: string;
    resetErrors: () => void;
    loading: boolean;
    label: string;
    testId: string;
}

const Password = ({
    password,
    setPassword,
    error,
    resetErrors,
    loading,
    label = 'Пароль',
    testId = 'password',
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
