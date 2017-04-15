import { ProductImage } from '../product-image';
export class ProductImageDescription {
    constructor(
        public id?: number,
        public title?: string,
        public altTag?: string,
        public name?: string,
        public description?: string,
        public productImage?: ProductImage,
    ) {
    }
}
