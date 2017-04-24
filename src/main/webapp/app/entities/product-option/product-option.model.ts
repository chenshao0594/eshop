import { ProductOptionDescription } from '../product-option-description';
import { MerchantStore } from '../merchant-store';
export class ProductOption {
    constructor(
        public id?: number,
        public readOnly?: boolean,
        public productOptionType?: string,
        public code?: string,
        public productOptionSortOrder?: number,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
        public descriptions?: ProductOptionDescription,
        public merchantStore?: MerchantStore,
    ) {
        this.readOnly = false;
    }
}
