import { MerchantStore } from '../merchant-store';
export class Optin {
    constructor(
        public id?: number,
        public description?: string,
        public startDate?: any,
        public code?: string,
        public endDate?: any,
        public merchant?: MerchantStore,
    ) {
    }
}
