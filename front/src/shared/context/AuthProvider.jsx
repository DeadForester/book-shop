import {useEffect, useMemo, useState} from "react";
import {AuthContext} from "../../context/auth.js";

const AuthProvider = ({children}) => {
    const [isAuth, setIsAuth] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    const value= useMemo(() => ({
        isAuth,
        setIsAuth,
        isLoading
    }), [isAuth, isLoading]);

    useEffect(() => {
        const saved = localStorage.getItem('auth');
        setIsAuth(saved === 'true');
        setIsLoading(false);
    }, []);

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;