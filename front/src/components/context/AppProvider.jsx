import AuthProvider from "./AuthProvider.jsx";
import BasketProvider from "./BasketProvider.jsx";

const AppProvider = ({ children }) => {
    return (
        <AuthProvider>
            <BasketProvider>
                {children}
            </BasketProvider>
        </AuthProvider>
    );
};

export default AppProvider;