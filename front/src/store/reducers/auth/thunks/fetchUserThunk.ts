import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

import UserService from '@/api/UserService.ts';
import { User } from '@/models/db/user';
import { AUTH_STORAGE_KEY, AUTH_STORAGE_TYPE_KEY } from '@/shared/constants/auth-storage.ts';
import { clearErrors } from '@/store/reducers/auth/authSlice.ts';

export const fetchUser = createAsyncThunk<User, void, { rejectValue: string }>(
    'auth/fetchUser',
    async (_, { dispatch, rejectWithValue }) => {
        dispatch(clearErrors());

        const user = (
            localStorage.getItem(AUTH_STORAGE_TYPE_KEY) === 'local' ? localStorage : sessionStorage
        ).getItem(AUTH_STORAGE_KEY);

        const userId = JSON.parse(user ?? '{}')?.user_id;

        if (!userId) {
            return rejectWithValue('userId не найден в localStorage');
        }

        try {
            const response = await UserService.getUserById(Number(userId));
            return response.data;
        } catch (error: unknown) {
            if (axios.isAxiosError(error)) {
                return rejectWithValue(
                    error.response?.data?.message ?? 'Ошибка загрузки пользователя'
                );
            }
            if (error instanceof Error) {
                return rejectWithValue(error.message);
            }
            return rejectWithValue('Неизвестная ошибка загрузки пользователя');
        }
    }
);
