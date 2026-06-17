import $api from '@/http';

import { AuthResponse } from '../../models/response/Auth/AuthResponse.ts';

export default class AuthService {
    static async login(email: string, password: string) {
        return $api.post<AuthResponse>('/login', {
            email,
            password,
        });
    }

    static async registration(email: string, password: string) {
        return $api.post<AuthResponse>('/registration', {
            email,
            password,
        });
    }

    static async logout(): Promise<void> {
        return $api.post('/logout');
    }
}
