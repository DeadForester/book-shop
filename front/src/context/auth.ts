import { createContext } from 'react';
import { AuthContextType } from '@/shared/types/AuthContextType.ts';

export const AuthContext = createContext<AuthContextType | null>(null);
