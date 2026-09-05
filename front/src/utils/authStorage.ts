import { AuthResponse } from '@/models/response/auth/AuthResponse.ts';

type StorageType = 'local' | 'session';

const AUTH_STORAGE_KEY = 'auth_data';

export const authStorage = {
    save(data: AuthResponse, rememberMe: boolean) {
        this.clear();

        const storage = rememberMe ? localStorage : sessionStorage;
        storage.setItem(AUTH_STORAGE_KEY, JSON.stringify(data));
    },

    load(): AuthResponse | null {
        const storage = this.getStorageType();

        if (!storage) {
            return null;
        }

        if (storage === 'local') {
            const localData = localStorage.getItem(AUTH_STORAGE_KEY);
            if (localData) {
                return JSON.parse(localData);
            }
        }

        if (storage === 'session') {
            const sessionData = sessionStorage.getItem(AUTH_STORAGE_KEY);
            if (sessionData) {
                return JSON.parse(sessionData);
            }
        }

        return null;
    },

    saveStorageType(type: StorageType) {
        localStorage.setItem('auth_storage_type', type);
    },

    getStorageType(): StorageType | null {
        const storage = localStorage.getItem('auth_storage_type');

        if (storage !== 'local' && storage !== 'session') {
            return null;
        }

        return storage;
    },

    clear() {
        localStorage.removeItem(AUTH_STORAGE_KEY);
        sessionStorage.removeItem(AUTH_STORAGE_KEY);
        localStorage.removeItem('auth_storage_type');
    },
};