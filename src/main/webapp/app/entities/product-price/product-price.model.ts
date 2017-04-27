
const enum ProductPriceType {
    'ONE_TIME',
    'MONTHLY'

};
import { ProductPriceDescription } from '../product-price-description';
import { ProductAvailability } from '../product-availability';
export class ProductPrice {
    constructor(
        public id?: number,
        public defaultPrice?: boolean,
        public dEFAULTPRICECODE?: string,
        public code?: string,
        public productPriceSpecialEndDate?: any,
        public productPriceAmount?: number,
        public productPriceSpecialAmount?: number,
        public productPriceType?: ProductPriceType,
        public productPriceSpecialStartDate?: any,
        public createdBy?: string,
        public createdDate?: any,
        public lastModifiedBy?: string,
        public lastModifiedDate?: any,
        public descriptions?: ProductPriceDescription,
        public productAvailability?: ProductAvailability,
    ) {
        this.defaultPrice = false;
    }
}
