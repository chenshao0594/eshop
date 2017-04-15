
const enum OrderValueType {
    'ONE_TIME',
    'MONTHLY'

};

const enum OrderTotalType {
    'SHIPPING',
    'HANDLING',
    'TAX',
    'PRODUCT',
    'SUBTOTAL',
    'TOTAL',
    'CREDIT',
    'REFUND'

};
import { SalesOrder } from '../sales-order';
export class OrderTotal {
    constructor(
        public id?: number,
        public value?: number,
        public orderTotalCode?: string,
        public text?: string,
        public orderValueType?: OrderValueType,
        public sortOrder?: number,
        public orderTotalType?: OrderTotalType,
        public title?: string,
        public module?: string,
        public order?: SalesOrder,
    ) {
    }
}
