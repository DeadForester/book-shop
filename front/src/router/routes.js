import Books from "../pages/Books.jsx";
import Error from "../pages/Error.jsx";

export const privateRoutes = [{ path: "/panel", element: null }];

export const publicRoutes = [
    {
        path: "/books",
        element: Books,
    },
    { path: "/books/:id", element: null },
    { path: "/login", element: null },
    { path: "*", element: Error },
];
