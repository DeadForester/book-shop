import { createSlice } from '@reduxjs/toolkit';

import { AuthState } from '@/shared/types/AuthState.ts';
import { fetchUser } from '@/store/reducers/auth/thunks/featchUserThunk.ts';
import { login } from '@/store/reducers/auth/thunks/loginThunk.ts';
import { registration } from '@/store/reducers/auth/thunks/registrationThunk.ts';

const initialState: AuthState = {
    isAuth: localStorage.getItem('remember') === 'true',
    currentUser: null,
    isLoginLoading: false,
    isRegistrationLoading: false,
    isUserLoading: false,
    loginError: null,
    registrationError: null,
    userError: null,
};

export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        logout: (state) => {
            state.isAuth = false;
            state.currentUser = null;
            localStorage.removeItem('userId');
            localStorage.removeItem('remember');
        },
        clearErrors: (state) => {
            state.loginError = null;
            state.registrationError = null;
            state.userError = null;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(login.pending, (state) => {
                state.isLoginLoading = true;
                state.loginError = null;
            })
            .addCase(login.fulfilled, (state) => {
                state.isLoginLoading = false;
                state.isAuth = true;
            })
            .addCase(login.rejected, (state, action) => {
                state.isLoginLoading = false;
                state.loginError = action.payload ?? 'Ошибка входа :(';
            });

        builder
            .addCase(registration.pending, (state) => {
                state.isRegistrationLoading = true;
                state.registrationError = null;
            })
            .addCase(registration.fulfilled, (state) => {
                state.isRegistrationLoading = false;
            })
            .addCase(registration.rejected, (state, action) => {
                state.isRegistrationLoading = false;
                state.registrationError = action.payload ?? 'Ошибка регистрации :(';
            });

        builder
            .addCase(fetchUser.pending, (state) => {
                state.isUserLoading = true;
                state.userError = null;
            })
            .addCase(fetchUser.fulfilled, (state, action) => {
                state.isUserLoading = false;
                state.currentUser = action.payload;
            })
            .addCase(fetchUser.rejected, (state, action) => {
                state.isUserLoading = false;
                state.userError = action.payload ?? 'Ошибка при загрузке пользователя :(';
            });
    },
});

export const { logout, clearErrors } = authSlice.actions;
export default authSlice.reducer;
