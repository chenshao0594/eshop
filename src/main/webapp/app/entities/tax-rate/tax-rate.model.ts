import { TaxRateDescription } from '../tax-rate-description';
import { Country } from '../country';
import { TaxClass } from '../tax-class';
import { MerchantStore } from '../merchant-store';
import { Zone } from '../zone';
export class TaxRate {
    constructor(
        public id?: number,
        public piggyback?: boolean,
        public stateProvince?: string,
        public taxPriority?: number,
        public code?: string,
        public taxRate?: number,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
        public descriptions?: TaxRateDescription,
        public taxRates?: TaxRate,
        public country?: Country,
        public parent?: TaxRate,
        public taxClass?: TaxClass,
        public merchantStore?: MerchantStore,
        public zone?: Zone,
    ) {
        this.piggyback = false;
    }
}
