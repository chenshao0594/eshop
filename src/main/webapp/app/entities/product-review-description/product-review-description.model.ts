import { ProductReview } from '../product-review';
export class ProductReviewDescription {
    constructor(
        public id?: number,
        public title?: string,
        public name?: string,
        public description?: string,
        public productReview?: ProductReview,
    ) {
    }
}
