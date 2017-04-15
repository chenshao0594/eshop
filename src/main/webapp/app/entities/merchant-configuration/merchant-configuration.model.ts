
const enum MerchantConfigurationType {
    'INTEGRATION',
    'SHOP',
    'CONFIG'

};
export class MerchantConfiguration {
    constructor(
        public id?: number,
        public merchantConfigurationType?: MerchantConfigurationType,
        public key?: string,
        public value?: string,
    ) {
    }
}
