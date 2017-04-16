import { Language } from '../language';
import { Product } from '../product';
export class ProductDescription {
    constructor(
        public id?: number,
        public metatagDescription?: string,
        public seUrl?: string,
        public metatagKeywords?: string,
        public productHighlight?: string,
        public title?: string,
        public description?: string,
        public metatagTitle?: string,
        public name?: string,
        public productExternalDl?: string,
        public language?: Language,
        public product?: Product,
    ) {
    }
}
