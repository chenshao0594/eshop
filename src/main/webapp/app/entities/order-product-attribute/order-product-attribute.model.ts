import { OrderProduct } from '../order-product';
export class OrderProductAttribute {
    constructor(
        public id?: number,
        public productAttributeName?: string,
        public productAttributeWeight?: number,
        public productOptionValueId?: number,
        public productAttributePrice?: number,
        public productAttributeIsFree?: boolean,
        public productOptionId?: number,
        public productAttributeValueName?: string,
        public orderProduct?: OrderProduct,
    ) {
        this.productAttributeIsFree = false;
    }
}
