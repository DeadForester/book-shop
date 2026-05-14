import {BrowserRouter} from "react-router-dom";
import {useState} from "react";
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
                    cartOpen={isCartOpen}
                    closeCart={() => setCartOpen(false)}
                />
            </BrowserRouter>
        </AppProvider>

    );
}

export default App;
