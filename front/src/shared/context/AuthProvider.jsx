import {useMemo, useState} from "react";
import {AuthContext} from "../../context/auth.js";
import { useFetching } from '../../hooks/useFetching.js';
import AuthService from '../../API/AuthService.js';

const AuthProvider = ({children}) => {
    const [isAuth, setIsAuth] = useState(localStorage.getItem('remember') === 'true');

    const [login, isLoginLoading, loginError] = useFetching(async (email, password, rememberMe) => {
        const response = await AuthService.login(email, password);
        if (rememberMe) {
            localStorage.setItem('remember', 'true');
        }
        localStorage.setItem('userId', response.data.user_id);
        setIsAuth(true);
    });

    const [registration, isRegistrationLoading, registrationError] = useFetching(
        async (email, password) => {
            await AuthService.registration(email, password);
        }
    );
    
    const logout = () => {
        setIsAuth(false);
        localStorage.removeItem('userId');
        localStorage.removeItem('remember');
    }

    const value = useMemo(
        () => ({
            isAuth,
            login,
            isLoginLoading,
            loginError,
            registration,
            isRegistrationLoading,
            registrationError,
            logout,
        }),
        [
            isAuth,
            isLoginLoading,
            isRegistrationLoading,
            login,
            loginError,
            registration,
            registrationError,
        ]
    );

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;