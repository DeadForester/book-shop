import { Store } from '@reduxjs/toolkit';
import { useStore } from 'react-redux';

import { RootState } from '@/store/store.ts';

export const useAppStore = useStore.withTypes<Store<RootState>>();
