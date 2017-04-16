import { Zone } from '../zone';
import { Language } from '../language';
export class ZoneDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public zone?: Zone,
        public language?: Language,
    ) {
    }
}
