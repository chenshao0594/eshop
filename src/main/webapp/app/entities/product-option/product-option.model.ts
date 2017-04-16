import { ProductOptionDescription } from '../product-option-description';
import { MerchantStore } from '../merchant-store';
export class ProductOption {
    constructor(
        public id?: number,
        public readOnly?: boolean,
        public productOptionType?: string,
        public code?: string,
        public productOptionSortOrder?: number,
        public descriptions?: ProductOptionDescription,
        public merchantStore?: MerchantStore,
    ) {
        this.readOnly = false;
    }
}
