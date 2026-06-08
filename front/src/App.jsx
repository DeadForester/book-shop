import { useState } from 'react';
import Header from './shared/components/Header.tsx';
import Basket from './components/basket/Basket.jsx';
import AppRouter from './shared/components/AppRouter.tsx';
import { useLocation } from 'react-router-dom';

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
