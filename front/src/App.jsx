import {useState} from "react";
import Header from "./shared/components/Header.jsx";
import Basket from "./shared/components/Basket.jsx";
import AppRouter from "./shared/components/AppRouter.jsx";


const App = () => {
    const [isCartOpen, setCartOpen] = useState(false);

    return (
        <>
            <Header
                handleCart={() => setCartOpen(true)}
            />

            <AppRouter/>

            <Basket
                cartOpen={isCartOpen}
                closeCart={() => setCartOpen(false)}
            />
        </>
    );
}

export default App;
