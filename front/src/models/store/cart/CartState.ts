import { CartItem } from '@/store/reducers/cart/cartSlice.ts';

export interface CartState {
    items: CartItem[];
    isOpen: boolean;
}