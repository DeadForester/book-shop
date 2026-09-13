import { configureStore } from '@reduxjs/toolkit';
import type { Meta, StoryObj } from '@storybook/react-vite';
import { Provider } from 'react-redux';

import { AuthState } from '@/models/store/auth/AuthState';
import Loader from '@/shared/components/Loader';
import authReducer from '@/store/reducers/auth/authSlice';

import UserCircle from './UserCircle';

const createMockStore = (authOverrides: Partial<AuthState>) => {
    const initialState: AuthState = {
        isAuth: false,
        currentUser: null,
        isCheckingAuth: false,
        isLoginLoading: false,
        isRegistrationLoading: false,
        isUserLoading: false,
        loginError: null,
        registrationError: null,
        userError: null,
        ...authOverrides,
    };

    return configureStore({
        reducer: { auth: authReducer },
        preloadedState: { auth: initialState },
    });
};

const meta: Meta<typeof UserCircle> = {
    title: 'Shared/UserCircle',
    component: UserCircle,
    tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof Loader>;

export const LoggedOut: Story = {
    decorators: [
        (Story) => (
            <Provider store={createMockStore({ isAuth: false, currentUser: null })}>
                <Story />
            </Provider>
        ),
    ],
};

export const LoggedIn: Story = {
    decorators: [
        (Story) => (
            <Provider
                store={createMockStore({
                    isAuth: true,
                    currentUser: {
                        user_id: 1,
                        email: 'user@gmail.com',
                        user_role: 'USER',
                    },
                })}
            >
                <Story />
            </Provider>
        ),
    ],
};

export const WithError: Story = {
    decorators: [
        (Story) => (
            <Provider
                store={createMockStore({
                    isAuth: false,
                    currentUser: null,
                    userError: 'Ошибка при загрузке пользователя :(',
                })}
            >
                <Story />
            </Provider>
        ),
    ],
};