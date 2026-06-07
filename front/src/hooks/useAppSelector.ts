import { useSelector } from 'react-redux';
import { RootState } from '@/store/store.ts';

export const useAppSelector = useSelector.withTypes<RootState>();
