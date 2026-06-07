import { createAsyncThunk } from '@reduxjs/toolkit';
import AuthService from '@/api/auth/AuthService.ts';

export const registration = createAsyncThunk<
    void,
    { email: string; password: string },
    { rejectValue: string }
>('auth/registration', async ({ email, password }, { rejectWithValue }) => {
    try {
        await AuthService.registration(email, password);
    } catch (error: any) {
        return rejectWithValue(error.response?.data?.message || 'Ошибка регистрации');
    }
});
