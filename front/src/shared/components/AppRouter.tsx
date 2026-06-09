import { useEffect } from 'react';
import { Route, Routes } from 'react-router-dom';

import { useAppSelector } from '@/hooks/useAppSelector.ts';
import Books from '@/pages/Books.js';
import Error from '@/pages/Error.js';
import { adminRoutes, publicRoutes, userRoutes } from '@/router/routes.ts';

import ProtectedRoute from './ProtectedRoute.tsx';

const AppRouter = () => {
    const { currentUser, isAuth } = useAppSelector((state) => state.auth);

    useEffect(() => {
        console.log('Router user: ');
        console.log(currentUser);
    }, [currentUser]);

    return (
        <Routes>
            <Route path="/" element={<Books />} />
            {publicRoutes.map((route, index) => (
                <Route key={`public-${index}`} path={route.path} element={<route.element />} />
            ))}

            <Route element={<ProtectedRoute />}>
                {isAuth &&
                    userRoutes.map((route, index) => (
                        <Route
                            key={`private-${index}`}
                            path={route.path}
                            element={<route.element />}
                        />
                    ))}
                {currentUser?.isAdmin &&
                    adminRoutes.map((route, index) => (
                        <Route
                            key={`private-${index}`}
                            path={route.path}
                            element={<route.element />}
                        />
                    ))}
            </Route>

            <Route path="*" element={<Error />} />
        </Routes>
    );
};

export default AppRouter;
