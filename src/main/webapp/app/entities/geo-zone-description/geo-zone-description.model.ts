import { GeoZone } from '../geo-zone';
import { Language } from '../language';
export class GeoZoneDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public geoZone?: GeoZone,
        public language?: Language,
    ) {
    }
}
