import { lazy } from 'react';

const BookIdPage = lazy(() => import('@/pages/BookIdPage.tsx'));
const Books = lazy(() => import('@/pages/Books.tsx'));
const Login = lazy(() => import('@/pages/Login.tsx'));
const OrderHistory = lazy(() => import('@/pages/OrderHistory.tsx'));
const Profile = lazy(() => import('@/pages/Profile.tsx'));
const Register = lazy(() => import('@/pages/Register.tsx'));
const SupplierOrder = lazy(() => import('@/pages/SupplierOrder.tsx'));

export const adminRoutes = [{ path: '/storageOrder', element: SupplierOrder }];

export const userRoutes = [
    { path: '/orders', element: OrderHistory },
    { path: '/profile', element: Profile },
];

export const publicRoutes = [
    { path: '/books', element: Books },
    { path: '/books/:id', element: BookIdPage },
    { path: '/login', element: Login },
    { path: '/register', element: Register },
];
