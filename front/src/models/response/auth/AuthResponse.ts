import { UserRole } from '@/models/db/user/UserRole.ts';

export interface AuthResponse {
    accessToken?: string;
    refreshToken?: string;
    user_id: number;
    email: string;
    user_role: UserRole;
}
