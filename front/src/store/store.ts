import { configureStore } from '@reduxjs/toolkit';
import cartReducer from './reducers/cartSlice';
import authReducer from './reducers/authSlice';

export const store = configureStore({
    reducer: {
        books: booksReducer,
        cart: cartReducer,
        auth: authReducer,
    },
});

// 🔹 Типы для RootState и AppDispatch — основа всей типизации
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
