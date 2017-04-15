import { ProductOptionValueDescription } from '../product-option-value-description';
export class ProductOptionValue {
    constructor(
        public id?: number,
        public productOptionValueImage?: string,
        public code?: string,
        public productOptionValueSortOrder?: number,
        public productOptionDisplayOnly?: boolean,
        public descriptions?: ProductOptionValueDescription,
    ) {
        this.productOptionDisplayOnly = false;
    }
}
