import { Navigate, Outlet } from 'react-router-dom';
import { useAuthContext } from '../../hooks/useAuthContext.js';

const ProtectedRoute = () => {
    const { isAuth } = useAuthContext();

    return isAuth ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;
