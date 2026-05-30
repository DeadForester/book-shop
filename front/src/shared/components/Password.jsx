import { IconButton, InputAdornment, TextField } from '@mui/material';
import { useState } from 'react';
import { Visibility, VisibilityOff } from '@mui/icons-material';

const Password = ({ password, setPassword, error, resetErrors, loading, label = 'Пароль' }) => {
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
            }}
            disabled={loading}
        />
    );
};

export default Password;
