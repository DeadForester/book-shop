import UserService from '../../api/user/UserService.ts';
import { useFetching } from '../../hooks/useFetching.ts';
import { useCallback, useMemo, useState } from 'react';
import { UserContext } from '../../context/user.ts';
import { mockUser } from '../../data/user.ts';

const UserProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState(mockUser);

    const fetchUser = useCallback(async () => {
        const response = await UserService.getUserById(localStorage.getItem('userId'));

        setCurrentUser({ ...response.data, isAdmin: response.data.user_role === 'ADMIN' });
    }, []);

    const [getUser, isLoading, error] = useFetching(fetchUser);

    const value = useMemo(
        () => ({
            currentUser,
            getUser,
            isLoading,
            error,
        }),
        [currentUser, error, getUser, isLoading]
    );

    return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
};

export default UserProvider;
