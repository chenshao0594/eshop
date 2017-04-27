import { ZoneDescription } from '../zone-description';
import { Country } from '../country';
export class Zone {
    constructor(
        public id?: number,
        public code?: string,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
        public descriptions?: ZoneDescription,
        public country?: Country,
    ) {
    }
}
