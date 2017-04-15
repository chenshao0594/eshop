import { Country } from '../country';
import { GeoZoneDescription } from '../geo-zone-description';
export class GeoZone {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public countries?: Country,
        public descriptions?: GeoZoneDescription,
    ) {
    }
}
