import { lazy } from 'react';

import {
    BOOKS_ROUTE,
    LOGIN_ROUTE,
    ORDER_HISTORY_ROUTE,
    PROFILE_ROUTE,
    REGISTER_ROUTE,
    STORAGE_ORDER_ROUTE,
} from '@/shared/constants/route-paths.ts';

const BookIdPage = lazy(() => import('@/pages/BookIdPage.tsx'));
const Books = lazy(() => import('@/pages/Books.tsx'));
const Login = lazy(() => import('@/pages/Login.tsx'));
const OrderHistory = lazy(() => import('@/pages/OrderHistory.tsx'));
const Profile = lazy(() => import('@/pages/Profile.tsx'));
const Register = lazy(() => import('@/pages/Register.tsx'));
const SupplierOrder = lazy(() => import('@/pages/SupplierOrder.tsx'));

export const adminRoutes = [{ path: STORAGE_ORDER_ROUTE, element: SupplierOrder }];

export const userRoutes = [
    { path: ORDER_HISTORY_ROUTE, element: OrderHistory },
    { path: PROFILE_ROUTE, element: Profile },
];

export const publicRoutes = [
    { path: BOOKS_ROUTE, element: Books },
    { path: `${BOOKS_ROUTE}/:id`, element: BookIdPage },
    { path: LOGIN_ROUTE, element: Login },
    { path: REGISTER_ROUTE, element: Register },
];
