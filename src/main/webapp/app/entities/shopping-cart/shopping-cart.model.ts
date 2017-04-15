import { ShoppingCartItem } from '../shopping-cart-item';
export class ShoppingCart {
    constructor(
        public id?: number,
        public customerId?: number,
        public shoppingCartCode?: string,
        public lineItems?: ShoppingCartItem,
    ) {
    }
}
