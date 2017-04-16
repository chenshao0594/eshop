import { ProductOptionValueDescription } from '../product-option-value-description';
import { MerchantStore } from '../merchant-store';
export class ProductOptionValue {
    constructor(
        public id?: number,
        public productOptionValueImage?: string,
        public code?: string,
        public productOptionValueSortOrder?: number,
        public productOptionDisplayOnly?: boolean,
        public descriptions?: ProductOptionValueDescription,
        public merchantStore?: MerchantStore,
    ) {
        this.productOptionDisplayOnly = false;
    }
}
