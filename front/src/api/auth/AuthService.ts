import axios from 'axios';

import { LoginResponse, RegisterResponse } from '@/api/auth/types.ts';

export default class AuthService {
    static async login(email: string, password: string) {
        return await axios.post<LoginResponse>('http://localhost:8080/api/v1/login', {
            email,
            password,
        });
    }

    static async registration(email: string, password: string) {
        return await axios.post<RegisterResponse>(`http://localhost:8080/api/v1/registration`, {
            email,
            password,
        });
    }
}
