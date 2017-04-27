
const enum TransactionType {
    'INIT',
    'AUTHORIZE',
    'CAPTURE',
    'AUTHORIZECAPTURE',
    'REFUND'

};

const enum PaymentType {
    'CREDITCARD',
    'FREE',
    'COD',
    'MONEYORDER',
    'PAYPAL',
    'STRIPE',
    'WEPAY'

};
import { SalesOrder } from '../sales-order';
export class Transaction {
    constructor(
        public id?: number,
        public details?: string,
        public transactionType?: TransactionType,
        public transactionDate?: any,
        public paymentType?: PaymentType,
        public amount?: number,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
        public order?: SalesOrder,
    ) {
    }
}
