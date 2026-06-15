import { useCallback, useRef } from 'react';

type AnyFunction = (...args: unknown[]) => unknown;

export default function useDebounce<T extends AnyFunction>(callback: T, delay: number = 500) {
    const timer = useRef<ReturnType<typeof setTimeout> | null>(null);

    return useCallback(
        (...args: Parameters<T>) => {
            if (timer.current) clearTimeout(timer.current);

            timer.current = setTimeout(() => {
                callback(...args);
            }, delay);
        },
        [callback, delay]
    );
}
