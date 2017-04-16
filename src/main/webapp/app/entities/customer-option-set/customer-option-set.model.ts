import { CustomerOption } from '../customer-option';
import { CustomerOptionValue } from '../customer-option-value';
export class CustomerOptionSet {
    constructor(
        public id?: number,
        public sortOrder?: number,
        public customerOption?: CustomerOption,
        public customerOptionValue?: CustomerOptionValue,
    ) {
    }
}
