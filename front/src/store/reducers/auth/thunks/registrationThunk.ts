import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

import AuthService from '@/api/AuthService.ts';
import {AuthResponse} from "@/models/response/auth/AuthResponse.ts";
import { clearErrors } from '@/store/reducers/auth/authSlice.ts';
import handleAuthSuccess from "@/utils/handleAuthSuccess.ts";

export const registration = createAsyncThunk<
    AuthResponse,
    { email: string; password: string },
    { rejectValue: string }
>('auth/registration', async ({ email, password }, { dispatch, rejectWithValue }) => {
    dispatch(clearErrors());
    try {
        const response = await AuthService.registration(email, password);

        return await handleAuthSuccess(response.data, false, rejectWithValue);
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
