import { Product } from '../product';
import { MerchantStore } from '../merchant-store';
export class ProductRelationship {
    constructor(
        public id?: number,
        public code?: string,
        public active?: boolean,
        public product?: Product,
        public relatedProduct?: Product,
        public store?: MerchantStore,
    ) {
        this.active = false;
    }
}
