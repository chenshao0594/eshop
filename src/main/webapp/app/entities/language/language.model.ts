import { MerchantStore } from '../merchant-store';
export class Language {
    constructor(
        public id?: number,
        public code?: string,
        public sortOrder?: number,
        public storesDefaultLanguage?: MerchantStore,
        public stores?: MerchantStore,
    ) {
    }
}
