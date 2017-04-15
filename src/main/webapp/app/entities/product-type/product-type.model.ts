export class ProductType {
    constructor(
        public id?: number,
        public code?: string,
        public gENERALTYPE?: string,
        public allowAddToCart?: boolean,
    ) {
        this.allowAddToCart = false;
    }
}
