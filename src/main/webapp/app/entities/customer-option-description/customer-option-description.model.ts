import { CustomerOption } from '../customer-option';
import { Language } from '../language';
export class CustomerOptionDescription {
    constructor(
        public id?: number,
        public title?: string,
        public customerOptionComment?: string,
        public name?: string,
        public description?: string,
        public customerOption?: CustomerOption,
        public language?: Language,
    ) {
    }
}
