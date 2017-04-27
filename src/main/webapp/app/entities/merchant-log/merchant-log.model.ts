import { MerchantStore } from '../merchant-store';
export class MerchantLog {
    constructor(
        public id?: number,
        public log?: string,
        public module?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
        public store?: MerchantStore,
    ) {
    }
}
