import BookIdPage from '../pages/BookIdPage.tsx';
import Books from '../pages/Books.tsx';
import Login from '../pages/Login.tsx';
import OrderHistory from '../pages/OrderHistory.tsx';
import Profile from '../pages/Profile.tsx';
import Register from '../pages/Register.tsx';
import SupplierOrder from '../pages/SupplierOrder.tsx';

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
