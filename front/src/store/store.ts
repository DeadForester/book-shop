import { configureStore } from '@reduxjs/toolkit';

import authReducer from './reducers/auth/authSlice.ts';
import cartReducer from './reducers/cart/cartSlice.ts';

export const store = configureStore({
    reducer: {
        cart: cartReducer,
        auth: authReducer,
    },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
