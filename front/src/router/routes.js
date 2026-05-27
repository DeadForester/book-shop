import Books from "../pages/Books.jsx";
import BookIdPage from "../pages/BookIdPage.jsx";
import Dashboard from "../pages/Dashboard.jsx";
import Login from "../pages/Login.jsx";
import Register from "../pages/Register.jsx";
import OrderHistory from "../pages/OrderHistory.jsx";

export const privateRoutes = [
    { path: "/panel", element: Dashboard },
    { path: "/history", element: OrderHistory },
];

export const publicRoutes = [
    { path: "/books",element: Books },
    { path: "/books/:id", element: BookIdPage },
    { path: "/login", element: Login },
    { path: "/register", element: Register },
];
