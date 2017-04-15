import { OrderAccountProduct } from '../order-account-product';
export class OrderAccount {
    constructor(
        public id?: number,
        public orderAccountStartDate?: any,
        public orderAccountEndDate?: any,
        public orderAccountBillDay?: number,
        public orderAccountProducts?: OrderAccountProduct,
    ) {
    }
}
