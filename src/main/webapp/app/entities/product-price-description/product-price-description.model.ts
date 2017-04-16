import { ProductPrice } from '../product-price';
import { Language } from '../language';
export class ProductPriceDescription {
    constructor(
        public id?: number,
        public title?: string,
        public dEFAULTPRICEDESCRIPTION?: string,
        public name?: string,
        public description?: string,
        public productPrice?: ProductPrice,
        public language?: Language,
    ) {
    }
}
