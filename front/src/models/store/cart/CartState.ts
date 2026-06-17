import { CartItem } from './CartItem';

export interface CartState {
    items: CartItem[];
    isOpen: boolean;
}