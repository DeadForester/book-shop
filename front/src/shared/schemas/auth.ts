import { z } from 'zod';

export const loginSchema = z.object({
    email: z.email('Некорректный email').min(1, 'Email обязателен'),
    password: z
        .string()
        .min(6, 'Пароль должен содержать минимум 6 цифр')
        .max(16, 'Пароль слишком длинный'),
});

export const registerSchema = z
    .object({
        email: z.email('Некорректный email').min(1, 'Email обязателен'),
        password: z
            .string()
            .min(6, 'Пароль должен содержать минимум 6 цифр')
            .max(16, 'Пароль слишком длинный'),
        confirmPassword: z.string(),
    })
    .refine((data) => data.password === data.confirmPassword, {
        message: 'Пароли не совпадают',
        path: ['confirmPassword'],
    });

export type LoginInput = z.infer<typeof loginSchema>;
export type RegisterInput = z.infer<typeof registerSchema>;
