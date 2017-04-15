import { ProductOptionDescription } from '../product-option-description';
export class ProductOption {
    constructor(
        public id?: number,
        public readOnly?: boolean,
        public productOptionType?: string,
        public code?: string,
        public productOptionSortOrder?: number,
        public descriptions?: ProductOptionDescription,
    ) {
        this.readOnly = false;
    }
}
