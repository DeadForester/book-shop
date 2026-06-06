import { useContext } from 'react';
import { AuthContext } from '../context/auth.ts';
import { AuthContextType } from '@/shared/types/AuthContextType.ts';

export const useAuthContext = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuthContext must be used within the context');
    }
    return context;
};
