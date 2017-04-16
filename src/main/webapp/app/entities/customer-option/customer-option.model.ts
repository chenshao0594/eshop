import { CustomerOptionDescription } from '../customer-option-description';
import { MerchantStore } from '../merchant-store';
export class CustomerOption {
    constructor(
        public id?: number,
        public active?: boolean,
        public customerOptionType?: string,
        public code?: string,
        public publicOption?: boolean,
        public sortOrder?: number,
        public descriptions?: CustomerOptionDescription,
        public merchantStore?: MerchantStore,
    ) {
        this.active = false;
        this.publicOption = false;
    }
}
