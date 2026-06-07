import { User } from '@/shared/types/User.ts';
import { createAsyncThunk } from '@reduxjs/toolkit';
import UserService from '@/api/user/UserService.ts';

export const fetchUser = createAsyncThunk<User, void, { rejectValue: string }>(
    'auth/fetchUser',
    async (_, { rejectWithValue }) => {
        const userId = localStorage.getItem('userId');

        if (!userId) {
            return rejectWithValue('userId не найден в localStorage');
        }

        try {
            const response = await UserService.getUserById(userId);
            return { ...response.data, isAdmin: response.data.user_role === 'ADMIN' };
        } catch (error: any) {
            return rejectWithValue(error.response?.data?.message || 'Ошибка загрузки пользователя');
        }
    }
);
