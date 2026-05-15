import Books from "../pages/Books.jsx";
import Error from "../pages/Error.jsx";
import BookIdPage from "../pages/BookIdPage.jsx";

export const privateRoutes = [{ path: "/panel", element: null }];

export const publicRoutes = [
    {
        path: "/books",
        element: Books,
    },
    { path: "/books/:id", element: BookIdPage },
    { path: "/login", element: null },
    { path: "*", element: Error },
];
