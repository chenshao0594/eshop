import { ProductPrice } from '../product-price';
import { Product } from '../product';
export class ProductAvailability {
    constructor(
        public id?: number,
        public productQuantityOrderMax?: number,
        public productIsAlwaysFreeShipping?: boolean,
        public region?: string,
        public productQuantity?: number,
        public productQuantityOrderMin?: number,
        public productDateAvailable?: any,
        public productStatus?: boolean,
        public regionVariant?: string,
        public prices?: ProductPrice,
        public product?: Product,
    ) {
        this.productIsAlwaysFreeShipping = false;
        this.productStatus = false;
    }
}
