
const enum OrderStatus {
    'ORDERED',
    'PROCESSED',
    'DELIVERED',
    'REFUNDED',
    'CANCELED'

};
import { SalesOrder } from '../sales-order';
export class OrderStatusHistory {
    constructor(
        public id?: number,
        public comments?: string,
        public customerNotified?: number,
        public dateAdded?: any,
        public status?: OrderStatus,
        public order?: SalesOrder,
    ) {
    }
}
