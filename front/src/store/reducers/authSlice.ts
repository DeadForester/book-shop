import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    items: [],
    isOpen: false,
};

export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {},
});
