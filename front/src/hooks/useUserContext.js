import { useContext } from 'react';
import { UserContext } from '../context/user.js';

export const useUserContext = () => useContext(UserContext);
