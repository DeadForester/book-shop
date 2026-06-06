import { useContext } from 'react';
import { BasketContext } from '../context/basket.ts';

export const useBasketContext = () => useContext(BasketContext);
