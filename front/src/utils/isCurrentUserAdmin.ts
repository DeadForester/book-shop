import { User } from '@/models/db/user';

const isCurrentUserAdmin = (currentUser: User | null) => 
    currentUser?.user_role === 'ADMIN';

export default isCurrentUserAdmin;