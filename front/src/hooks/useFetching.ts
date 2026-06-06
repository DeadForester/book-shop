import { useCallback, useState } from 'react';

export const useFetching = <T extends (...args: any[]) => Promise<any>>(callback: T) => {
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');

    const fetching = useCallback(
        async (...args: Parameters<T>) => {
            try {
                setIsLoading(true);
                await callback(...args);
            } catch (e: any) {
                if ('message' in e) {
                    setError(e.message);
                } else {
                    throw e;
                }
            } finally {
                setIsLoading(false);
            }
        },
        [callback]
    ) as T;

    return [fetching, isLoading, error];
};
