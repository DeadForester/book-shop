import Books from '../pages/Books.jsx';
import BookIdPage from '../pages/BookIdPage.tsx';
import Login from '../pages/Login.jsx';
import Register from '../pages/Register.jsx';
import OrderHistory from '../pages/OrderHistory.tsx';
import Profile from '../pages/Profile.tsx';
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
