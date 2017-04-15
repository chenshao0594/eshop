import { CustomerOptionDescription } from '../customer-option-description';
export class CustomerOption {
    constructor(
        public id?: number,
        public active?: boolean,
        public customerOptionType?: string,
        public code?: string,
        public publicOption?: boolean,
        public sortOrder?: number,
        public descriptions?: CustomerOptionDescription,
    ) {
        this.active = false;
        this.publicOption = false;
    }
}
