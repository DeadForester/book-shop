import { User } from '@/shared/types/models/User';

export interface AuthState {
    isAuth: boolean;
    currentUser: User | null;
    isLoginLoading: boolean;
    isRegistrationLoading: boolean;
    isUserLoading: boolean;
    loginError: string | null;
    registrationError: string | null;
    userError: string | null;
}
