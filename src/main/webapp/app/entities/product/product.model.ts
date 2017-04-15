import { ProductImage } from '../product-image';
import { ProductAvailability } from '../product-availability';
import { ProductAttribute } from '../product-attribute';
import { ProductDescription } from '../product-description';
import { ProductRelationship } from '../product-relationship';
import { TaxClass } from '../tax-class';
export class Product {
    constructor(
        public id?: number,
        public productHeight?: number,
        public productWeight?: number,
        public productShipeable?: boolean,
        public productOrdered?: number,
        public productReviewAvg?: number,
        public dateAvailable?: any,
        public sortOrder?: number,
        public productIsFree?: boolean,
        public available?: boolean,
        public productReviewCount?: number,
        public refSku?: string,
        public productVirtual?: boolean,
        public productWidth?: number,
        public preOrder?: boolean,
        public productLength?: number,
        public sku?: string,
        public images?: ProductImage,
        public availabilities?: ProductAvailability,
        public attributes?: ProductAttribute,
        public descriptions?: ProductDescription,
        public relationships?: ProductRelationship,
        public taxClass?: TaxClass,
    ) {
        this.productShipeable = false;
        this.productIsFree = false;
        this.available = false;
        this.productVirtual = false;
        this.preOrder = false;
    }
}
