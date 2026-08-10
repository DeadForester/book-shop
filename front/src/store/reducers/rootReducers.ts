import { combineReducers } from '@reduxjs/toolkit';

import authReducer from './auth/authSlice.ts';
import cartReducer from './cart/cartSlice.ts';

export const rootReducers = combineReducers({
    cart: cartReducer,
    auth: authReducer,
});
