import { User } from '@/shared/types/User.ts';

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
