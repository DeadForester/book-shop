import { beforeEach, describe, expect, test, vi } from 'vitest';

import UserService from '@/api/UserService.ts';
import { fetchUser } from '@/store/reducers/auth/thunks/fetchUserThunk.ts';

vi.mock('@/api/UserService');

const dispatch = vi.fn();
const getState = vi.fn();

describe('fetch user thunk', () => {
    beforeEach(() => {
        vi.clearAllMocks();
        localStorage.clear();
    });

    test('Fetch success: User', async () => {
        localStorage.setItem('userId', '1');

        const mockResponse = {
            data: {
                user_id: 1,
                email: 'user@gmail.com',
                user_role: 'USER',
            },
        };

        vi.mocked(UserService.getUserById).mockResolvedValue(mockResponse as never);

        const result = await fetchUser()(dispatch, getState, undefined);

        expect(UserService.getUserById).toHaveBeenCalledWith(1);
        expect(result.type).toBe('auth/fetchUser/fulfilled');
        expect(result.payload).toEqual({
            ...mockResponse.data,
            isAdmin: false,
        });
    });

    test('Fetch success: Admin', async () => {
        localStorage.setItem('userId', '2');

        const mockResponse = {
            data: {
                user_id: 2,
                email: 'admin@gmail.com',
                user_role: 'ADMIN',
            },
        };

        vi.mocked(UserService.getUserById).mockResolvedValue(mockResponse as never);

        const result = await fetchUser()(dispatch, getState, undefined);

        expect(UserService.getUserById).toHaveBeenCalledWith(2);
        expect(result.type).toBe('auth/fetchUser/fulfilled');
        expect(result.payload).toEqual({
            ...mockResponse.data,
            isAdmin: true,
        });
    });

    test('Fetch failure: local storage have no userId', async () => {
        const result = await fetchUser()(dispatch, getState, undefined);

        expect(UserService.getUserById).not.toHaveBeenCalled();
        expect(result.type).toBe('auth/fetchUser/rejected');
        expect(result.payload).toBe('userId не найден в localStorage');
    });
});
