import { ProductReviewDescription } from '../product-review-description';
import { Customer } from '../customer';
export class ProductReview {
    constructor(
        public id?: number,
        public reviewRead?: number,
        public reviewDate?: any,
        public status?: number,
        public reviewRating?: number,
        public descriptions?: ProductReviewDescription,
        public customer?: Customer,
    ) {
    }
}
