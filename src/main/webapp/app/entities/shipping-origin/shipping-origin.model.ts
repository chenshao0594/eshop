import { Zone } from '../zone';
import { Country } from '../country';
import { MerchantStore } from '../merchant-store';
export class ShippingOrigin {
    constructor(
        public id?: number,
        public city?: string,
        public postalCode?: string,
        public address?: string,
        public active?: boolean,
        public state?: string,
        public zone?: Zone,
        public country?: Country,
        public merchantStore?: MerchantStore,
    ) {
        this.active = false;
    }
}
