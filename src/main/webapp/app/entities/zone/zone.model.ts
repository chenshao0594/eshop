import { ZoneDescription } from '../zone-description';
import { Country } from '../country';
export class Zone {
    constructor(
        public id?: number,
        public code?: string,
        public created_by?: string,
        public created_date?: any,
        public last_modified_by?: string,
        public last_modified_date?: any,
        public descriptions?: ZoneDescription,
        public country?: Country,
    ) {
    }
}
