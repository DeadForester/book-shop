import {BrowserRouter} from "react-router-dom";
import {BasketContext} from "./context/basket.js";
import {useMemo, useState} from "react";
import Header from "./shared/components/Header.jsx";
import Basket from "./components/Basket.jsx";
import AppRouter from "./components/AppRouter.jsx";
import AppProvider from "./components/context/AppProvider.jsx";


const App = () => {
    const [isCartOpen, setCartOpen] = useState(false);

    return (
        <AppProvider>
            <BrowserRouter>
                <Header
                    handleCart={() => setCartOpen(true)}
                />

                <AppRouter/>

                <Basket
                    order={order}
                    removeFormOrder={removeFromOrder}
                    cartOpen={isCartOpen}
                    closeCart={() => setCartOpen(false)}
                />
            </BrowserRouter>
        </AppProvider>

    );
}

export default App;
