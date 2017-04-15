import { OrderProduct } from '../order-product';
export class OrderProductPrice {
    constructor(
        public id?: number,
        public productPrice?: number,
        public productPriceCode?: string,
        public productPriceSpecialStartDate?: any,
        public productPriceSpecial?: number,
        public productPriceSpecialEndDate?: any,
        public productPriceName?: string,
        public defaultPrice?: boolean,
        public orderProduct?: OrderProduct,
    ) {
        this.defaultPrice = false;
    }
}
