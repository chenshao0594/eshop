import { CustomerOption } from '../customer-option';
export class CustomerOptionDescription {
    constructor(
        public id?: number,
        public title?: string,
        public customerOptionComment?: string,
        public name?: string,
        public description?: string,
        public customerOption?: CustomerOption,
    ) {
    }
}
