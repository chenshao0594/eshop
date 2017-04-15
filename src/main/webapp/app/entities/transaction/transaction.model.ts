
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
export class Transaction {
    constructor(
        public id?: number,
        public details?: string,
        public transactionType?: TransactionType,
        public transactionDate?: any,
        public paymentType?: PaymentType,
        public amount?: number,
    ) {
    }
}
