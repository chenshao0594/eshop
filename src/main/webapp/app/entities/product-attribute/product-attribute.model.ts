import { Product } from '../product';
export class ProductAttribute {
    constructor(
        public id?: number,
        public productAttributeWeight?: number,
        public productAttributePrice?: number,
        public attributeRequired?: boolean,
        public attributeDefault?: boolean,
        public attributeDisplayOnly?: boolean,
        public productOptionSortOrder?: number,
        public productAttributeIsFree?: boolean,
        public attributeDiscounted?: boolean,
        public product?: Product,
    ) {
        this.attributeRequired = false;
        this.attributeDefault = false;
        this.attributeDisplayOnly = false;
        this.productAttributeIsFree = false;
        this.attributeDiscounted = false;
    }
}
