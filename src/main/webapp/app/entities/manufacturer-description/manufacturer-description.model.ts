import { Manufacturer } from '../manufacturer';
import { Language } from '../language';
export class ManufacturerDescription {
    constructor(
        public id?: number,
        public urlClicked?: number,
        public title?: string,
        public url?: string,
        public name?: string,
        public dateLastClick?: any,
        public description?: string,
        public manufacturer?: Manufacturer,
        public language?: Language,
    ) {
    }
}
