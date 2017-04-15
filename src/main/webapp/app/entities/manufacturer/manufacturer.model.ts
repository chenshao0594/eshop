import { ManufacturerDescription } from '../manufacturer-description';
export class Manufacturer {
    constructor(
        public id?: number,
        public order?: number,
        public image?: string,
        public code?: string,
        public descriptions?: ManufacturerDescription,
    ) {
    }
}
