import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

import AuthService from '@/api/AuthService.ts';

export const registration = createAsyncThunk<
    void,
    { email: string; password: string },
    { rejectValue: string }
>('auth/registration', async ({ email, password }, { rejectWithValue }) => {
    try {
        await AuthService.registration(email, password);
    } catch (error: unknown) {
        if (axios.isAxiosError(error)) {
            return rejectWithValue(error.response?.data?.message ?? 'Ошибка регистрации аккаунта');
        }
        if (error instanceof Error) {
            return rejectWithValue(error.message);
        }
        return rejectWithValue('Неизвестная ошибка регистрации аккаунта');
    }
});
