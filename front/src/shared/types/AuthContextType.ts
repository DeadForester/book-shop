export interface AuthContextType {
    isAuth: boolean;
    login: (email: string, password: string, rememberMe: boolean) => Promise<void>;
    isLoginLoading: boolean;
    loginError: string;
    registration: (email: string, password: string) => Promise<void>;
    isRegistrationLoading: boolean;
    registrationError: string;
    logout: () => void;
}
