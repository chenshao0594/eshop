import { Product } from '../product';
import { TaxRate } from '../tax-rate';
import { MerchantStore } from '../merchant-store';
export class TaxClass {
    constructor(
        public id?: number,
        public title?: string,
        public code?: string,
        public products?: Product,
        public taxRates?: TaxRate,
        public merchantStore?: MerchantStore,
    ) {
    }
}
