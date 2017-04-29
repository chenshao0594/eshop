import { ProductOptionDescription } from '../product-option-description';
import { MerchantStore } from '../merchant-store';
export class ProductOption {
    constructor(
        public id?: number,
        public readOnly?: boolean,
        public productOptionType?: string,
        public code?: string,
        public productOptionSortOrder?: number,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
        public descriptions?: ProductOptionDescription,
        public merchantStore?: MerchantStore,
    ) {
        this.readOnly = true;
    }
}
