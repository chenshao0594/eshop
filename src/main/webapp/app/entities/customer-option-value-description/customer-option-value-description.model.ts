import { CustomerOptionValue } from '../customer-option-value';
import { Language } from '../language';
export class CustomerOptionValueDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public customerOptionValue?: CustomerOptionValue,
        public language?: Language,
    ) {
    }
}
