import axios from 'axios';

export default class UserService {
    static async getUserById(id) {
        return await axios.get(`http://localhost:8080/api/v1/user/${id}`);
    }
}
