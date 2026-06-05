export interface LoginResponse {
    user_id: string;
    token?: string;
    email: string;
}

export interface RegisterResponse {
    message: string;
}
