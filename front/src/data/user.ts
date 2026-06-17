import { User } from '../../models/db/User';

export const mockUser: User = {
    email: 'alex.ivanov@bookshop.ru',
    name: 'Алексей Иванов',
    user_role: 'USER',
    isAdmin: false,
    phone: '+7 (9__) ___-__-__',
    joinDate: 'dd.mm.yyyy',
};
