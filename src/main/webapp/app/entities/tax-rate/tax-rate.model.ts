import { TaxRateDescription } from '../tax-rate-description';
import { Country } from '../country';
import { TaxClass } from '../tax-class';
import { MerchantStore } from '../merchant-store';
export class TaxRate {
    constructor(
        public id?: number,
        public piggyback?: boolean,
        public stateProvince?: string,
        public taxPriority?: number,
        public code?: string,
        public taxRate?: number,
        public descriptions?: TaxRateDescription,
        public taxRates?: TaxRate,
        public country?: Country,
        public parent?: TaxRate,
        public taxClass?: TaxClass,
        public merchantStore?: MerchantStore,
    ) {
        this.piggyback = false;
    }
}
