import {useContext} from "react";
import {BasketContext} from "../basket.js";

export const useBasketContext = () => useContext(BasketContext);