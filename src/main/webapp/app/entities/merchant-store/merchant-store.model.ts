import { Language } from '../language';
export class MerchantStore {
    constructor(
        public id?: number,
        public storeaddress?: string,
        public code?: string,
        public storename?: string,
        public storeEmailAddress?: string,
        public dEFAULTSTORE?: string,
        public storephone?: string,
        public weightunitcode?: string,
        public useCache?: boolean,
        public storeTemplate?: string,
        public domainName?: string,
        public invoiceTemplate?: string,
        public storeLogo?: string,
        public inBusinessSince?: any,
        public currencyFormatNational?: boolean,
        public storepostalcode?: string,
        public seizeunitcode?: string,
        public storestateprovince?: string,
        public continueshoppingurl?: string,
        public storecity?: string,
        public defaultLanguage?: Language,
        public languages?: Language,
    ) {
        this.useCache = false;
        this.currencyFormatNational = false;
    }
}
