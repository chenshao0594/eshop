import { ManufacturerDescription } from '../manufacturer-description';
import { MerchantStore } from '../merchant-store';
export class Manufacturer {
    constructor(
        public id?: number,
        public order?: number,
        public image?: string,
        public code?: string,
        public descriptions?: ManufacturerDescription,
        public merchantStore?: MerchantStore,
    ) {
    }
}
