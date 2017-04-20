
import { Billing } from '../billing';
const enum CustomerGender {
    'M',
    'F'

};
import { CustomerAttribute } from '../customer-attribute';
import { Delivery } from '../delivery';
import { ProductReview } from '../product-review';
import { MerchantStore } from '../merchant-store';
import { Language } from '../language';
export class Customer {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public dateOfBirth?: any,
        public gender?: CustomerGender,
        public anonymous?: boolean,
        public company?: string,
        public nick?: string,
        public emailAddress?: string,
        public password?: string,
        public attributes?: CustomerAttribute[],
        public reviews?: ProductReview[],
        public merchantStore?: MerchantStore,
        public defaultLanguage?: Language,
        public billing?: Billing,
        public delivery?: Delivery,
    ) {
        this.anonymous = false;
        this.billing = new Billing();
        this.delivery = new Delivery();
    }
}
