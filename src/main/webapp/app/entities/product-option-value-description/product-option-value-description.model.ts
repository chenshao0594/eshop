import { ProductOptionValue } from '../product-option-value';
export class ProductOptionValueDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public productOptionValue?: ProductOptionValue,
    ) {
    }
}
