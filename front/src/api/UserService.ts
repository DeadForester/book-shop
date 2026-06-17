import axios from 'axios';

import { API_URL } from '@/http';
import { User } from '@/models/db/User';

export default class UserService {
    static async getUserById(id: number) {
        return await axios.get<User>(`${API_URL}/user/${id}`);
    }
}
