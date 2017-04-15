
const enum PaymentType {
    'CREDITCARD',
    'FREE',
    'COD',
    'MONEYORDER',
    'PAYPAL',
    'STRIPE',
    'WEPAY'

};

const enum OrderChannel {
    'ONLINE'

};

const enum OrderType {
    'ORDER',
    'BOOKING'

};

const enum OrderStatus {
    'ORDERED',
    'PROCESSED',
    'DELIVERED',
    'REFUNDED',
    'CANCELED'

};
import { OrderTotal } from '../order-total';
import { OrderStatusHistory } from '../order-status-history';
import { OrderProduct } from '../order-product';
export class SalesOrder {
    constructor(
        public id?: number,
        public customerId?: number,
        public confirmedAddress?: boolean,
        public orderDateFinished?: any,
        public total?: number,
        public paymentModuleCode?: string,
        public paymentType?: PaymentType,
        public locale?: string,
        public channel?: OrderChannel,
        public customerEmailAddress?: string,
        public orderType?: OrderType,
        public status?: OrderStatus,
        public lastModified?: any,
        public currencyValue?: number,
        public datePurchased?: any,
        public shippingModuleCode?: string,
        public ipAddress?: string,
        public customerAgreement?: boolean,
        public orderTotal?: OrderTotal,
        public orderHistory?: OrderStatusHistory,
        public orderProducts?: OrderProduct,
    ) {
        this.confirmedAddress = false;
        this.customerAgreement = false;
    }
}
