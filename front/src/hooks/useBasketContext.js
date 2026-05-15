import {useContext} from "react";
import {BasketContext} from "../context/basket.js";

export const useBasketContext = () => useContext(BasketContext);