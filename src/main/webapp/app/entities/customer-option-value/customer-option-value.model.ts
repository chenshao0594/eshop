import { CustomerOptionValueDescription } from '../customer-option-value-description';
import { MerchantStore } from '../merchant-store';
export class CustomerOptionValue {
    constructor(
        public id?: number,
        public code?: string,
        public customerOptionValueImage?: string,
        public sortOrder?: number,
        public descriptions?: CustomerOptionValueDescription,
        public merchantStore?: MerchantStore,
    ) {
    }
}
