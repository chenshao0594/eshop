import { ShoppingCartItem } from '../shopping-cart-item';
import { MerchantStore } from '../merchant-store';
export class ShoppingCart {
    constructor(
        public id?: number,
        public customerId?: number,
        public shoppingCartCode?: string,
        public lineItems?: ShoppingCartItem,
        public merchantStore?: MerchantStore,
    ) {
    }
}
