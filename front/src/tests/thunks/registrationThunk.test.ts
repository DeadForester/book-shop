import { beforeEach, describe, expect, test, vi } from 'vitest';

import AuthService from '@/api/AuthService.ts';
import {UserRole} from "@/models/db/user/UserRole.ts";
import {registration} from "@/store/reducers/auth/thunks/registrationThunk.ts";
import {authStorage} from "@/utils/authStorage.ts";

vi.mock('@/api/AuthService');
vi.mock('@/utils/authStorage');

const dispatch = vi.fn();
const getState = vi.fn();

describe('registration thunk', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    test('registration success', async () => {
        const axiosSuccess = {
            data: {
                user_id: 1,
                email: 'user@gmail.com',
                user_role: 'USER' as UserRole,
            },
            status: 200,
            statusText: 'OK',
            headers: {},
            config: {
                headers: {},
            },
        };

        vi.mocked(AuthService.registration).mockResolvedValue(axiosSuccess as never);


        const result = await registration({
            email: 'user@gmail.com',
            password: 'password123',
        })(dispatch, getState, undefined);

        expect(AuthService.registration).toHaveBeenCalledWith('user@gmail.com', 'password123');

        expect(authStorage.save).toHaveBeenCalledWith(axiosSuccess.data, false);
        expect(authStorage.saveStorageType).toHaveBeenCalledWith('session');

        expect(result.type).toBe('auth/registration/fulfilled');
        expect(result.payload).toEqual(axiosSuccess.data);
    });

    test('Registration fail: unknown role', async () => {
        const axiosSuccess = {
            data: {
                user_id: 1,
                email: 'user@gmail.com',
                user_role: 'MANAGER' as UserRole,
            },
        };

        vi.mocked(AuthService.registration).mockResolvedValue(axiosSuccess as never);

        const result = await registration({
            email: 'user@gmail.com',
            password: 'password123',
        })(dispatch, getState, undefined);

        expect(authStorage.save).not.toHaveBeenCalled();
        expect(result.type).toBe('auth/registration/rejected');
        expect(result.payload).toBe('Неизвестная роль пользователя');
    });

    test('Registration fail: network error', async () => {
        const axiosError = {
            isAxiosError: true,
            response: {
                data: { message: 'Email уже занят' },
            },
        };

        vi.mocked(AuthService.registration).mockRejectedValue(axiosError);

        const result = await registration({
            email: 'user@gmail.com',
            password: 'password123',
        })(dispatch, getState, undefined);

        expect(result.type).toBe('auth/registration/rejected');
        expect(result.payload).toBe('Email уже занят');
    });
});
