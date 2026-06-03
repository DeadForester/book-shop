import { Route, Routes } from 'react-router-dom';

import { adminRoutes, publicRoutes, userRoutes } from '../../router/routes.js';

import Books from '../../pages/Books.jsx';
import { useAuthContext } from '../../hooks/useAuthContext.js';
import ProtectedRoute from './ProtectedRoute.jsx';
import Error from '../../pages/Error.jsx';
import { useUserContext } from '../../hooks/useUserContext.js';

const AppRouter = () => {
    const { isAuth } = useAuthContext();
    const { currentUser } = useUserContext();

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
                {currentUser.isAdmin &&
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
