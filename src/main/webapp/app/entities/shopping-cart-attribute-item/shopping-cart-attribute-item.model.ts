import { ShoppingCartItem } from '../shopping-cart-item';
export class ShoppingCartAttributeItem {
    constructor(
        public id?: number,
        public productAttributeId?: number,
        public shoppingCartItem?: ShoppingCartItem,
    ) {
    }
}
