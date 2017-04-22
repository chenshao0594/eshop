import { ProductOptionValueDescription } from '../product-option-value-description';
import { MerchantStore } from '../merchant-store';
import { ProductOption } from '../product-option';
export class ProductOptionValue {
    constructor(
        public id?: number,
        public productOptionValueImage?: string,
        public code?: string,
        public productOptionValueSortOrder?: number,
        public productOptionDisplayOnly?: boolean,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
        public descriptions?: ProductOptionValueDescription,
        public merchantStore?: MerchantStore,
        public productOption?: ProductOption,
    ) {
        this.productOptionDisplayOnly = false;
    }
}
