import { ProductImageDescription } from '../product-image-description';
import { Product } from '../product';
export class ProductImage {
    constructor(
        public id?: number,
        public productImage?: string,
        public productImageUrl?: string,
        public defaultImage?: boolean,
        public imageType?: number,
        public imageCrop?: boolean,
        public imageContent?: any,
        public descriptions?: ProductImageDescription,
        public product?: Product,
    ) {
        this.defaultImage = false;
        this.imageCrop = false;
    }
}
