import { ProductOption } from '../product-option';
export class ProductOptionDescription {
    constructor(
        public id?: number,
        public productOptionComment?: string,
        public title?: string,
        public name?: string,
        public description?: string,
        public productOption?: ProductOption,
    ) {
    }
}
