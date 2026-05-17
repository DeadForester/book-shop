export const validateCredentials = (email, password, setErrors) => {
    const newErrors = {};

    validateEmail(email, newErrors);

    validatePassword(password, setErrors);

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
};

const validateEmail = (email, newErrors) => {
    if (!email.trim()) {
        newErrors.email = 'Введите email';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
        newErrors.email = 'Некорректный формат email';
    }
}

const validatePassword = (password, newErrors) => {
    if (!password) {
        newErrors.password = 'Введите пароль';
    } else if (password.length < 6) {
        newErrors.password = 'Минимум 6 символов';
    }
}