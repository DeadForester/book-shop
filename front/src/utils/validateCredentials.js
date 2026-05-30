export const validateCredentials = (email, password, confirmPassword = null) => {
    const newErrors = {};

    validateEmail(email, newErrors);

    validatePassword(password, newErrors);

    console.log(confirmPassword);

    if (confirmPassword != null) validatePasswordConfirm(password, confirmPassword, newErrors);

    return newErrors;
};

const validateEmail = (email, newErrors) => {
    if (!email.trim()) {
        newErrors.email = 'Введите email';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
        newErrors.email = 'Некорректный формат email';
    }
};

const validatePassword = (password, newErrors) => {
    if (!password) {
        newErrors.password = 'Введите пароль';
    } else if (password.length < 6) {
        newErrors.password = 'Минимум 6 символов';
    }
};

const validatePasswordConfirm = (password, confirmPassword, newErrors) => {
    if (!confirmPassword) newErrors.confirmPassword = 'Повторите пароль';
    else if (password !== confirmPassword) newErrors.confirmPassword = 'Пароли не совпадают';
};
