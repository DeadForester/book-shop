import { UserRole } from '@/models/db/user/UserRole.ts';

export function isValidRole(role: unknown): role is UserRole {
    return role === 'USER' || role === 'ADMIN';
}