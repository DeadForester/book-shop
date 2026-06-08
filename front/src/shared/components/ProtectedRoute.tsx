import { Navigate, Outlet } from 'react-router-dom';

import { useAppSelector } from '@/hooks/useAppSelector.ts';

const ProtectedRoute = () => {
    const { isAuth } = useAppSelector((state) => state.auth);

    return isAuth ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;
