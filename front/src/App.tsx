import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';

import { useAppDispatch } from '@/hooks/useAppDispatch.ts';
import { useAppSelector } from '@/hooks/useAppSelector.ts';
import Loader from '@/shared/components/Loader.tsx';
import { finishCheckAuth, restoreAuth } from '@/store/reducers/auth/authSlice.ts';
import { authStorage } from '@/utils/authStorage.ts';

import Basket from './components/basket/Basket.tsx';
import AppRouter from './shared/components/AppRouter.tsx';
import Header from './shared/components/Header.tsx';

const App = () => {
    const dispatch = useAppDispatch();
    const { isCheckingAuth } = useAppSelector((state) => state.auth);

    const [isCartOpen, setCartOpen] = useState(false);
    const location = useLocation();

    const hideHeaderPaths = ['/login', '/register'];
    const shouldShowHeader = !hideHeaderPaths.includes(location.pathname);

    useEffect(() => {
        const savedAuth = authStorage.load();

        if (savedAuth) {
            dispatch(restoreAuth(savedAuth));
        } else {
            dispatch(finishCheckAuth());
        }
    }, [dispatch]);

    if (isCheckingAuth) {
        return <Loader />;
    }

    return (
        <>
            {shouldShowHeader && <Header handleCart={() => setCartOpen(true)} />}

            <AppRouter />

            <Basket cartOpen={isCartOpen} closeCart={() => setCartOpen(false)} />
        </>
    );
};

export default App;
