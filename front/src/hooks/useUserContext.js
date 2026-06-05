import { useContext } from 'react';
import { UserContext } from '../context/user.ts';

export const useUserContext = () => useContext(UserContext);
