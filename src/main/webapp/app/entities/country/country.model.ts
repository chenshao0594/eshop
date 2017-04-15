import { CountryDescription } from '../country-description';
import { Zone } from '../zone';
import { GeoZone } from '../geo-zone';
export class Country {
    constructor(
        public id?: number,
        public isoCode?: string,
        public supported?: boolean,
        public descriptions?: CountryDescription,
        public zones?: Zone,
        public geoZone?: GeoZone,
    ) {
        this.supported = false;
    }
}
