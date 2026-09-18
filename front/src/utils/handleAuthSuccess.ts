import { AuthResponse } from '@/models/response/auth/AuthResponse.ts';
import { authStorage } from '@/utils/authStorage.ts';
import { isValidRole } from '@/utils/isValidRole.ts';

export default async function handleAuthSuccess<RejectedValue>(
    data: AuthResponse,
    rememberMe: boolean,
    rejectWithValue: (value: string) => RejectedValue
) {
    if (!isValidRole(data.user_role)) {
        return rejectWithValue('Неизвестная роль пользователя');
    }

    authStorage.save(data, rememberMe);
    authStorage.saveStorageType(rememberMe ? 'local' : 'session');

    return data;
}