import { Country } from '../country';
export class CountryDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public country?: Country,
    ) {
    }
}
