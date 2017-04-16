import { CustomerOptionValue } from '../customer-option-value';
import { CustomerOption } from '../customer-option';
import { Customer } from '../customer';
export class CustomerAttribute {
    constructor(
        public id?: number,
        public textValue?: string,
        public customerOptionValue?: CustomerOptionValue,
        public customerOption?: CustomerOption,
        public customer?: Customer,
    ) {
    }
}
