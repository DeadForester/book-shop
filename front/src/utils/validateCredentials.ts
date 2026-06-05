import { CredentialsErrors } from '@/utils/CredentialsErrors.ts';

export const validateCredentials = (
    email: string,
    password: string,
    confirmPassword: string | null = null
) => {
    const newErrors: CredentialsErrors = {
        email: '',
        password: '',
        confirmPassword: '',
    };

    validateEmail(email, newErrors);

    validatePassword(password, newErrors);

    if (confirmPassword != null) validatePasswordConfirm(password, confirmPassword, newErrors);

    return newErrors;
};

const validateEmail = (email: string, newErrors: CredentialsErrors) => {
    if (!email.trim()) {
        newErrors.email = 'Введите email';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
        newErrors.email = 'Некорректный формат email';
    }
};

const validatePassword = (password: string, newErrors: CredentialsErrors) => {
    if (!password) {
        newErrors.password = 'Введите пароль';
    } else if (password.length < 6) {
        newErrors.password = 'Минимум 6 символов';
    }
};

const validatePasswordConfirm = (
    password: string,
    confirmPassword: string,
    newErrors: CredentialsErrors
) => {
    if (!confirmPassword) newErrors.confirmPassword = 'Повторите пароль';
    else if (password !== confirmPassword) newErrors.confirmPassword = 'Пароли не совпадают';
};
