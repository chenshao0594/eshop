import { Product } from '../product';
export class DigitalProduct {
    constructor(
        public id?: number,
        public productFileName?: string,
        public product?: Product,
    ) {
    }
}
