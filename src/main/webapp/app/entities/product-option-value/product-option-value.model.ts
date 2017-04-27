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
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
        public descriptions?: ProductOptionValueDescription,
        public merchantStore?: MerchantStore,
        public productOption?: ProductOption,
    ) {
        this.productOptionDisplayOnly = false;
    }
}
