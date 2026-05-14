import {useCallback, useMemo, useState} from "react";
import {BasketContext} from "../../context/basket.js";

const BasketProvider = ({children}) => {
    const [order, setOrder] = useState([]);

    const removeFromOrder = useCallback((goodsItem) => {
        setOrder(order.filter((item) => item.id !== goodsItem));
    }, [order]);

    const value = useMemo(() => ({
        order,
        setOrder,
        removeFromOrder
    }), [order, removeFromOrder]);

    return (
        <BasketContext.Provider value={value}>
            {children}
        </BasketContext.Provider>
    );
};

export default BasketProvider;