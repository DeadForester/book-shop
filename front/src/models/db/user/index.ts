import { UserRole } from './UserRole.ts';

export interface User {
    user_id: number;
    email: string;
    name?: string;
    user_role: UserRole;
    phone?: string;
    joinDate?: Date | string;
}
