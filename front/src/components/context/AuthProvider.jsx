import {useMemo, useState} from "react";
import {AuthContext} from "../../context/auth.js";

const AuthProvider = ({children}) => {
    const [isAuth, setIsAuth] = useState(false);

    const value= useMemo(() => ({
        isAuth,
        setIsAuth
    }), [isAuth]);

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;