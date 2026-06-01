import Books from '../pages/Books.jsx';
import BookIdPage from '../pages/BookIdPage.jsx';
import Dashboard from '../pages/Dashboard.jsx';
import Login from '../pages/Login.jsx';
import Register from '../pages/Register.jsx';
import OrderHistory from '../pages/OrderHistory.jsx';
import Profile from '../pages/Profile.jsx';
import SupplierOrder from '../pages/SupplierOrder.jsx';

export const adminRoutes = [
    { path: '/panel', element: Dashboard },
    { path: '/storageOrder', element: SupplierOrder },
];

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
