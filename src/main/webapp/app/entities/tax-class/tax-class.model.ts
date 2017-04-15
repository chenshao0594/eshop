import { Product } from '../product';
import { TaxRate } from '../tax-rate';
export class TaxClass {
    constructor(
        public id?: number,
        public title?: string,
        public code?: string,
        public products?: Product,
        public taxRates?: TaxRate,
    ) {
    }
}
