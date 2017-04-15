
const enum CustomerGender {
    'M',
    'F'

};
import { CustomerAttribute } from '../customer-attribute';
import { ProductReview } from '../product-review';
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
        public attributes?: CustomerAttribute,
        public reviews?: ProductReview,
    ) {
        this.anonymous = false;
    }
}
