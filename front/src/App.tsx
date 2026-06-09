import { useState } from 'react';
import { useLocation } from 'react-router-dom';

import Basket from './components/basket/Basket.tsx';
import AppRouter from './shared/components/AppRouter.tsx';
import Header from './shared/components/Header.tsx';

const App = () => {
    const [isCartOpen, setCartOpen] = useState(false);
    const location = useLocation();

    const hideHeaderPaths = ['/login', '/register'];
    const shouldShowHeader = !hideHeaderPaths.includes(location.pathname);

    return (
        <>
            {shouldShowHeader && <Header handleCart={() => setCartOpen(true)} />}

            <AppRouter />

            <Basket cartOpen={isCartOpen} closeCart={() => setCartOpen(false)} />
        </>
    );
};

export default App;
