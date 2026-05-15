import {useCallback, useMemo, useState} from "react";
import {BasketContext} from "../../context/basket.js";

const BasketProvider = ({children}) => {
    const [order, setOrder] = useState([]);

    const removeFromOrder = useCallback((id) => {
        setOrder(prev => prev.filter(item => item.id !== id));
    }, []);

    const addToOrder = useCallback((goodsItem) => {
        setOrder(prev => {
            const index = prev.findIndex(item => item.id === goodsItem.id);

            if (index > -1) {
                // Обновляем количество у существующего товара
                return prev.map((item, i) =>
                    i === index ? { ...item, quantity: item.quantity + 1 } : item
                );
            }

            // Добавляем новый товар
            return [...prev, { ...goodsItem, quantity: 1 }];
        });
    }, []);

    const value = useMemo(() => ({
        order,
        removeFromOrder,
        addToOrder
    }), [addToOrder, order, removeFromOrder]);

    return (
        <BasketContext.Provider value={value}>
            {children}
        </BasketContext.Provider>
    );
};

export default BasketProvider;