import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

import AuthService from '@/api/AuthService.ts';
import { AuthResponse } from '@/models/response/auth/AuthResponse.ts';
import { clearErrors } from '@/store/reducers/auth/authSlice.ts';
import { authStorage } from '@/utils/authStorage.ts';
import { isValidRole } from '@/utils/isValidRole.ts';

export const login = createAsyncThunk<
    AuthResponse,
    { email: string; password: string; rememberMe: boolean },
    { rejectValue: string }
>('auth/login', async ({ email, password, rememberMe }, { dispatch, rejectWithValue }) => {
    dispatch(clearErrors());
    try {
        const response = await AuthService.login(email, password);

        const data = response.data;
        
        if (!isValidRole(data.user_role)) {
            return rejectWithValue('Неизвестная роль пользователя');
        }

        authStorage.save(data, rememberMe);

        authStorage.saveStorageType(rememberMe ? 'local' : 'session');

        return data;
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
