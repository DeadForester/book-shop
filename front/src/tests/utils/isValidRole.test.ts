import { describe, expect, test } from 'vitest';

import { isValidRole } from '@/utils/isValidRole.ts';

describe('isValidRole', () => {
    test('Valid USER role', () => {
        expect(isValidRole('USER')).toStrictEqual(true);
    });

    test('Valid ADMIN role', () => {
        expect(isValidRole('ADMIN')).toStrictEqual(true);
    });

    test('Valid unknown role', () => {
        expect(isValidRole('MANAGER')).toStrictEqual(false);
    });
});
