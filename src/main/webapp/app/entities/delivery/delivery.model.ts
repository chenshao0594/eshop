import { Country } from '../country';
export class Delivery {
    constructor(
        public id?: number,
        public company?: string,
        public firstName?: string,
        public lastName?: string,
        public address?: string,
        public city?: string,
        public state?: string,
        public postalCode?: string,
        public country?: Country,
    ) {
    }
}
