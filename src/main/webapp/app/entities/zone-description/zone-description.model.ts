import { Zone } from '../zone';
export class ZoneDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public zone?: Zone,
    ) {
    }
}
