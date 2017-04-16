import { Country } from '../country';
import { Language } from '../language';
export class CountryDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public country?: Country,
        public language?: Language,
    ) {
    }
}
