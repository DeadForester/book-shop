import axios from 'axios';

export default class AuthService {
    static async login(email, password) {
        return await axios.post(
            'http://localhost:8080/api/v1/login',
            { email, password },
            {
                headers: {
                    'Content-Type': 'application/json',
                },
                withCredentials: true,
            }
        );
    }

    static async registration(email, password) {
        return await axios.post(`http://localhost:8080/api/v1/registration`, { email, password });
    }
}
