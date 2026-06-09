export interface User {
    email: string;
    name: string;
    user_role: 'USER' | 'ADMIN';
    isAdmin: boolean;
    phone: string;
    joinDate: Date | string;
}
