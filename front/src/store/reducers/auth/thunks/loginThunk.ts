import { createAsyncThunk } from '@reduxjs/toolkit';
import AuthService from '@/api/auth/AuthService.ts';

export const login = createAsyncThunk<
    { user_id: string },
    { email: string; password: string; rememberMe: boolean },
    { rejectValue: string }
>('auth/login', async ({ email, password, rememberMe }, { rejectWithValue }) => {
    try {
        const response = await AuthService.login(email, password);
        if (rememberMe) {
            localStorage.setItem('remember', 'true');
        }
        localStorage.setItem('userId', response.data.user_id);
        return response.data;
    } catch (error: any) {
        return rejectWithValue(error.response?.data?.message || 'Ошибка входа');
    }
});
