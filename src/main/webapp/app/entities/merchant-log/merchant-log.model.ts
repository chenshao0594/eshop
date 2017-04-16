import { MerchantStore } from '../merchant-store';
export class MerchantLog {
    constructor(
        public id?: number,
        public log?: string,
        public module?: string,
        public store?: MerchantStore,
    ) {
    }
}
