import { Customer } from '../customer';
export class CustomerAttribute {
    constructor(
        public id?: number,
        public textValue?: string,
        public customer?: Customer,
    ) {
    }
}
