
const enum CustomerGender {
    'M',
    'F'

};
import { CustomerAttribute } from '../customer-attribute';
import { ProductReview } from '../product-review';
import { MerchantStore } from '../merchant-store';
import { Language } from '../language';
export class Customer {
    constructor(
        public id?: number,
        public dateOfBirth?: any,
        public gender?: CustomerGender,
        public anonymous?: boolean,
        public company?: string,
        public nick?: string,
        public emailAddress?: string,
        public password?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
        public attributes?: CustomerAttribute,
        public reviews?: ProductReview,
        public merchantStore?: MerchantStore,
        public defaultLanguage?: Language,
    ) {
        this.anonymous = false;
    }
}
