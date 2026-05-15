import {Route, Routes} from "react-router-dom";

import {privateRoutes, publicRoutes} from "../../router/routes.js";
import Loader from "../../components/UI/loader/Loader.jsx";
import Books from "../../pages/Books.jsx";
import {useAuthContext} from "../../context/hooks/useAuthContext.js";

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
            {isAuth && privateRoutes.map((route, index) =>
                <Route
                    key={`private-${index}`}
                    path={route.path}
                    element={<route.element/>}
                />
            )}
        </Routes>
    );
};

export default AppRouter;