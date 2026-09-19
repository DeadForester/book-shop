import { beforeEach, describe, expect, test, vi } from 'vitest';

import AuthService from '@/api/AuthService.ts';
import { login } from '@/store/reducers/auth/thunks/loginThunk';
import { authStorage } from '@/utils/authStorage.ts';
import { isValidRole } from '@/utils/isValidRole.ts';

vi.mock('@/api/AuthService');
vi.mock('@/utils/authStorage');
vi.mock('@/utils/isValidRole');

const dispatch = vi.fn();
const getState = vi.fn();

describe('login thunk', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    test('Login success', async () => {
        const mockResponse = {
            data: {
                user_id: 1,
                email: 'user@gmail.com',
                user_role: 'USER',
            },
        };

        vi.mocked(AuthService.login).mockResolvedValue(mockResponse as never);
        vi.mocked(isValidRole).mockReturnValue(true);

        const result = await login({
            email: 'user@gmail.com',
            password: 'password123',
            rememberMe: true,
        })(dispatch, getState, undefined);

        expect(AuthService.login).toHaveBeenCalledWith('user@gmail.com', 'password123');

        expect(authStorage.save).toHaveBeenCalledWith(mockResponse.data, true);
        expect(authStorage.saveStorageType).toHaveBeenCalledWith('local');

        expect(result.type).toBe('auth/login/fulfilled');
        expect(result.payload).toEqual(mockResponse.data);
    });

    test('Login failure by invalid role', async () => {
        const mockResponse = {
            data: {
                user_id: 1,
                email: 'user@gmail.com',
                user_role: 'MANAGER',
            },
        };

        vi.mocked(AuthService.login).mockResolvedValue(mockResponse as never);
        vi.mocked(isValidRole).mockReturnValue(false);

        const result = await login({
            email: 'user@gmail.com',
            password: 'password123',
            rememberMe: false,
        })(dispatch, getState, undefined);

        expect(authStorage.save).not.toHaveBeenCalledWith();

        expect(result.type).toBe('auth/login/rejected');
        expect(result.payload).toBe('Неизвестная роль пользователя');
    });

    test('Login failure by network error', async () => {
        const axiosError = {
            isAxiosError: true,
            response: {
                data: {
                    message: 'Неверный email или пароль',
                },
            },
        };

        vi.mocked(AuthService.login).mockRejectedValue(axiosError);
        vi.mocked(isValidRole).mockReturnValue(false);

        const result = await login({
            email: 'user@gmail.com',
            password: 'wrong-password',
            rememberMe: false,
        })(dispatch, getState, undefined);

        expect(result.type).toBe('auth/login/rejected');
        expect(result.payload).toBe('Неверный email или пароль');
    });
});
