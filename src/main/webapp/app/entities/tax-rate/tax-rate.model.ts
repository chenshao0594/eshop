import { TaxRateDescription } from '../tax-rate-description';
import { TaxClass } from '../tax-class';
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
        public taxClass?: TaxClass,
        public parent?: TaxRate,
    ) {
        this.piggyback = false;
    }
}
