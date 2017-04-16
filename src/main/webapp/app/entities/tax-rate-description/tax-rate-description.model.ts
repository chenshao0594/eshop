import { TaxRate } from '../tax-rate';
import { Language } from '../language';
export class TaxRateDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public taxRate?: TaxRate,
        public language?: Language,
    ) {
    }
}
