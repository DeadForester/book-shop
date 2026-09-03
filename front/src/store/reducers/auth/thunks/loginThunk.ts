import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

import AuthService from '@/api/AuthService.ts';

export const login = createAsyncThunk<
    { user_id: number },
    { email: string; password: string; rememberMe: boolean },
    { rejectValue: string }
>('auth/login', async ({ email, password, rememberMe }, { rejectWithValue }) => {
    try {
        const response = await AuthService.login(email, password);
        if (rememberMe) {
            localStorage.setItem('remember', 'true');
        }
        localStorage.setItem('userId', response.data.user_id.toString());
        return response.data;
    } catch (error: unknown) {
        if (axios.isAxiosError(error)) {
            return rejectWithValue(error.response?.data?.message ?? 'Ошибка входа в аккаунт');
        }
        if (error instanceof Error) {
            return rejectWithValue(error.message);
        }
        return rejectWithValue('Неизвестная ошибка входа в аккаунт');
    }
});
