import { describe, expect, test } from 'vitest';

import { validateCredentials } from '@/utils/validateCredentials.ts';

describe('validateCredentials', () => {
    test('Valid credentials login', () => {
        expect(validateCredentials('test@mail.ru', 'Password123')).toStrictEqual({});
    });

    test('Valid credentials register', () => {
        expect(validateCredentials('test@mail.ru', 'Password123', 'Password123')).toStrictEqual({});
    });

    test('Edge password: 6 symbols', () => {
        expect(validateCredentials('test@mail.ru', 'Passwo')).toStrictEqual({});
    });

    test('Edge password: 16 symbols', () => {
        expect(validateCredentials('test@mail.ru', 'Password12345678')).toStrictEqual({});
    });

    test('Wrong email', () => {
        expect(validateCredentials('testmail.ru', 'Password123')).not.toStrictEqual({});
    });

    test('Wrong password: short', () => {
        expect(validateCredentials('test@mail.ru', 'Pas')).not.toStrictEqual({});
    });

    test('Wrong password: long', () => {
        expect(
            validateCredentials('test@mail.ru', 'Password123Password123Password123')
        ).not.toStrictEqual({});
    });

    test('Passwords are not equal', () => {
        expect(
            validateCredentials('test@mail.ru', 'Password123', 'Password1234')
        ).not.toStrictEqual({});
    });
});
