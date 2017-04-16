import { OrderProduct } from '../order-product';
import { OrderAccount } from '../order-account';
export class OrderAccountProduct {
    constructor(
        public id?: number,
        public orderAccountProductId?: number,
        public orderAccountProductLastTransactionStatus?: number,
        public orderAccountProductEndDate?: any,
        public orderAccountProductStartDate?: any,
        public orderAccountProductLastStatusDate?: any,
        public orderAccountProductStatus?: number,
        public orderAccountProductAccountedDate?: any,
        public orderAccountProductPaymentFrequencyType?: number,
        public orderAccountProductEot?: any,
        public orderProduct?: OrderProduct,
        public orderAccount?: OrderAccount,
    ) {
    }
}
