import AuthProvider from "./AuthProvider.jsx";
import BasketProvider from "./BasketProvider.jsx";
import UserProvider from './UserProvider.jsx';

const AppProvider = ({ children }) => {
    return (
        <AuthProvider>
            <UserProvider>
                <BasketProvider>{children}</BasketProvider>
            </UserProvider>
        </AuthProvider>
    );
};

export default AppProvider;