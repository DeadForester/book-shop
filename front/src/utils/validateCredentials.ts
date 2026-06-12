import { z } from 'zod';

import { loginSchema, registerSchema } from '@/shared/schemas/auth';
import { CredentialsErrors } from '@/shared/types/CredentialsErrors.ts';

export const validateCredentials = (
    email: string,
    password: string,
    confirmPassword: string = ''
) => {
    try {
        if (!confirmPassword) {
            loginSchema.parse({ email, password });
        } else {
            registerSchema.parse({ email, password, confirmPassword });
        }
        return {};
    } catch (err) {
        if (err instanceof z.ZodError) {
            return z.flattenError(err).fieldErrors as CredentialsErrors;
        }
        return {};
    }
};
