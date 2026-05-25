import {useMemo, useState} from "react";
import {AuthContext} from "../../context/auth.js";

const AuthProvider = ({children}) => {
    const [isAuth, setIsAuth] = useState(localStorage.getItem('auth') === 'true');
    const [isLoading] = useState(false);

    const value= useMemo(() => ({
        isAuth,
        setIsAuth,
        isLoading
    }), [isAuth, isLoading]);

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;