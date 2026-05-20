import { Navigate, Outlet } from 'react-router-dom';
import Loader from '../../components/UI/loader/Loader.jsx';
import {useAuthContext} from "../../hooks/useAuthContext.js";

const ProtectedRoute = () => {
    const { isAuth, isLoading } = useAuthContext();

    if (isLoading) return <Loader />;

    return isAuth ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;