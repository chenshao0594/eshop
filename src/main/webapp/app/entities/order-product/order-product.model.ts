import { OrderProductDownload } from '../order-product-download';
import { OrderProductAttribute } from '../order-product-attribute';
import { OrderProductPrice } from '../order-product-price';
import { SalesOrder } from '../sales-order';
export class OrderProduct {
    constructor(
        public id?: number,
        public productQuantity?: number,
        public sku?: string,
        public oneTimeCharge?: number,
        public productName?: string,
        public downloads?: OrderProductDownload,
        public orderAttributes?: OrderProductAttribute,
        public prices?: OrderProductPrice,
        public order?: SalesOrder,
    ) {
    }
}
