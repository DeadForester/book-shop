import { Navigate, Outlet } from 'react-router-dom';

import { useAppSelector } from '@/hooks/useAppSelector.ts';
import { LOGIN_ROUTE } from '@/shared/constants/route-paths.ts';

const ProtectedRoute = () => {
    const { isAuth } = useAppSelector((state) => state.auth);

    return isAuth ? <Outlet /> : <Navigate to={LOGIN_ROUTE} replace />;
};

export default ProtectedRoute;
