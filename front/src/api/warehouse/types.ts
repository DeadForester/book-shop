export interface WarehouseValue {
    warehouseId: number | string;
    bookId: number | string;
    quantityToAdd: number;
}

export interface Warehouse {
    warehouse_id: number | string;
    placement: string;
}

export interface BookByWarehouseValue {
    warehouseId: number | string;
    bookId: number | string;
}
