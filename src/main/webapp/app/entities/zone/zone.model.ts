import { ZoneDescription } from '../zone-description';
import { Country } from '../country';
export class Zone {
    constructor(
        public id?: number,
        public code?: string,
        public descriptions?: ZoneDescription,
        public country?: Country,
    ) {
    }
}
