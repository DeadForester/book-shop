import { describe, expect, test } from 'vitest';

import { AuthResponse } from '@/models/response/auth/AuthResponse.ts';
import { AuthState } from '@/models/store/auth/AuthState.ts';
import authReducer, {
    clearErrors,
    finishCheckAuth,
    logout,
    restoreAuth,
} from '@/store/reducers/auth/authSlice';
import { fetchUser } from '@/store/reducers/auth/thunks/fetchUserThunk.ts';
import { login } from '@/store/reducers/auth/thunks/loginThunk';
import { registration } from '@/store/reducers/auth/thunks/registrationThunk.ts';

const initialState: AuthState = {
    isAuth: false,
    currentUser: null,
    isCheckingAuth: true,
    isLoginLoading: false,
    isRegistrationLoading: false,
    isUserLoading: false,
    loginError: null,
    registrationError: null,
    userError: null,
};

const testUser: AuthResponse = {
    user_id: 1,
    email: 'user@gmail.com',
    user_role: 'USER',
};

describe('AuthReducers', () => {
    test('finish check auth', () => {
        expect(authReducer(initialState, finishCheckAuth())).toEqual({
            ...initialState,
            isCheckingAuth: false,
        });
    });

    test('restore auth', () => {
        expect(authReducer(initialState, restoreAuth(testUser))).toEqual({
            ...initialState,
            isAuth: true,
            isCheckingAuth: false,
            currentUser: testUser,
        });
    });

    test('logout', () => {
        expect(
            authReducer(
                {
                    ...initialState,
                    isAuth: true,
                    isCheckingAuth: false,
                    currentUser: testUser,
                },
                logout()
            )
        ).toEqual({
            ...initialState,
            isAuth: false,
            currentUser: null,
            isCheckingAuth: false,
        });
    });

    test('clear errors', () => {
        expect(
            authReducer(
                {
                    ...initialState,
                    loginError: 'Some error',
                    registrationError: 'Some error',
                    userError: 'Some error',
                },
                clearErrors()
            )
        ).toEqual(initialState);
    });

    test('login pending', () => {
        expect(authReducer(initialState, { type: login.pending.type })).toEqual({
            ...initialState,
            isLoginLoading: true,
            loginError: null,
        });
    });

    test('registration pending', () => {
        expect(authReducer(initialState, { type: registration.pending.type })).toEqual({
            ...initialState,
            isRegistrationLoading: true,
            registrationError: null,
        });
    });

    test('fetch user pending', () => {
        expect(authReducer(initialState, { type: fetchUser.pending.type })).toEqual({
            ...initialState,
            isUserLoading: true,
            userError: null,
        });
    });

    test('login fulfilled', () => {
        expect(
            authReducer(initialState, { type: login.fulfilled.type, payload: testUser })
        ).toEqual({
            ...initialState,
            isLoginLoading: false,
            isAuth: true,
            currentUser: testUser,
        });
    });

    test('register fulfilled', () => {
        expect(authReducer(initialState, { type: registration.fulfilled.type, payload: testUser })).toEqual({
            ...initialState,
            isRegistrationLoading: false,
            isAuth: true,
            currentUser: testUser,
        });
    });

    test('fetch user fulfilled', () => {
        expect(
            authReducer(initialState, { type: fetchUser.fulfilled.type, payload: testUser })
        ).toEqual({
            ...initialState,
            isUserLoading: false,
            currentUser: testUser,
        });
    });

    test('login rejected with message', () => {
        expect(
            authReducer(initialState, { type: login.rejected.type, payload: 'Some error' })
        ).toEqual({
            ...initialState,
            isLoginLoading: false,
            loginError: 'Some error',
        });
    });

    test('login rejected without message', () => {
        expect(authReducer(initialState, { type: login.rejected.type, payload: null })).toEqual({
            ...initialState,
            isLoginLoading: false,
            loginError: 'Ошибка входа :(',
        });
    });

    test('registration rejected with message', () => {
        expect(
            authReducer(initialState, { type: registration.rejected.type, payload: 'Some error' })
        ).toEqual({
            ...initialState,
            isRegistrationLoading: false,
            registrationError: 'Some error',
        });
    });

    test('registration rejected without message', () => {
        expect(
            authReducer(initialState, { type: registration.rejected.type, payload: null })
        ).toEqual({
            ...initialState,
            isRegistrationLoading: false,
            registrationError: 'Ошибка регистрации :(',
        });
    });

    test('fetch user rejected with message', () => {
        expect(
            authReducer(initialState, { type: fetchUser.rejected.type, payload: 'Some error' })
        ).toEqual({
            ...initialState,
            isUserLoading: false,
            userError: 'Some error',
        });
    });

    test('fetch user rejected without message', () => {
        expect(authReducer(initialState, { type: fetchUser.rejected.type, payload: null })).toEqual(
            {
                ...initialState,
                isUserLoading: false,
                userError: 'Ошибка при загрузке пользователя :(',
            }
        );
    });
});
