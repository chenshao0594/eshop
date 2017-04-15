import { Product } from '../product';
export class ProductRelationship {
    constructor(
        public id?: number,
        public code?: string,
        public active?: boolean,
        public product?: Product,
    ) {
        this.active = false;
    }
}
