import { MerchantStore } from '../merchant-store';
export class MerchantLog {
    constructor(
        public id?: number,
        public log?: string,
        public module?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
        public store?: MerchantStore,
    ) {
    }
}
