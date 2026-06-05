import { useContext } from 'react';
import { AuthContext } from '../context/auth.ts';

export const useAuthContext = () => useContext(AuthContext);
