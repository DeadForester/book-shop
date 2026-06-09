import axios from 'axios';

import { User } from '@/shared/types/User.ts';

export default class UserService {
    static async getUserById(id: number | string) {
        return await axios.get<User>(`http://localhost:8080/api/v1/user/${id}`);
    }
}
