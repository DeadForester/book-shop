import Books from "../pages/Books.jsx";
import Error from "../pages/Error.jsx";

export const privateRoutes = [{ path: "/panel", element: <></> }];

export const publicRoutes = [
    {
        path: "/books",
        element: Books,
    },
    { path: "/books:id", element: <></> },
    { path: "/login", element: <></> },
    { path: "*", element: Error },
];
