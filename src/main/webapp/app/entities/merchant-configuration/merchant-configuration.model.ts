
const enum MerchantConfigurationType {
    'INTEGRATION',
    'SHOP',
    'CONFIG'

};
import { MerchantStore } from '../merchant-store';
export class MerchantConfiguration {
    constructor(
        public id?: number,
        public merchantConfigurationType?: MerchantConfigurationType,
        public key?: string,
        public value?: string,
        public merchantStore?: MerchantStore,
    ) {
    }
}
