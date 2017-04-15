import { CustomerOptionValueDescription } from '../customer-option-value-description';
export class CustomerOptionValue {
    constructor(
        public id?: number,
        public code?: string,
        public customerOptionValueImage?: string,
        public sortOrder?: number,
        public descriptions?: CustomerOptionValueDescription,
    ) {
    }
}
