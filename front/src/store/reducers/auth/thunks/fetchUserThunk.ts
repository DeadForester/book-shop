import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

import UserService from '@/api/UserService.ts';
import { User } from '@/models/db/user';
import { clearErrors } from '@/store/reducers/auth/authSlice.ts';

export const fetchUser = createAsyncThunk<User, void, { rejectValue: string }>(
    'auth/fetchUser',
    async (_, { dispatch, rejectWithValue }) => {
        dispatch(clearErrors());

        const userIdLS = localStorage.getItem('userId');

        if (!userIdLS) {
            return rejectWithValue('userId не найден в localStorage');
        }

        const userId = Number(userIdLS);

        try {
            const response = await UserService.getUserById(userId);
            return { ...response.data, isAdmin: response.data.user_role === 'ADMIN' };
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
