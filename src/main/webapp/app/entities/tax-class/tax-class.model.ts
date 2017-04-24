import { Product } from '../product';
import { TaxRate } from '../tax-rate';
import { MerchantStore } from '../merchant-store';
export class TaxClass {
    constructor(
        public id?: number,
        public title?: string,
        public code?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
        public products?: Product,
        public taxRates?: TaxRate,
        public merchantStore?: MerchantStore,
    ) {
    }
}
