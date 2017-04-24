import { ManufacturerDescription } from '../manufacturer-description';
import { MerchantStore } from '../merchant-store';
export class Manufacturer {
    constructor(
        public id?: number,
        public order?: number,
        public image?: string,
        public code?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
        public descriptions?: ManufacturerDescription,
        public merchantStore?: MerchantStore,
    ) {
    }
}
