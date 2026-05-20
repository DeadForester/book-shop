import {Route, Routes} from "react-router-dom";

import {privateRoutes, publicRoutes} from "../../router/routes.js";
import Loader from "../../components/UI/loader/Loader.jsx";
import Books from "../../pages/Books.jsx";
import {useAuthContext} from "../../hooks/useAuthContext.js";
import ProtectedRoute from "./ProtectedRoute.jsx";
import Error from "../../pages/Error.jsx";

const AppRouter = () => {
    const {isAuth, isLoading} = useAuthContext();

    if (isLoading) {
        return <Loader/>
    }

    return (
        <Routes>
            <Route path="/" element={<Books/>}/>
            {publicRoutes.map((route, index) =>
                <Route
                    key={`public-${index}`}
                    path={route.path}
                    element={<route.element/>}
                />
            )}

            <Route element={<ProtectedRoute />}>
                {isAuth && privateRoutes.map((route, index) =>
                    <Route
                        key={`private-${index}`}
                        path={route.path}
                        element={<route.element/>}
                    />
                )}
            </Route>

            <Route path="*" element={<Error />} />
        </Routes>
    );
};

export default AppRouter;