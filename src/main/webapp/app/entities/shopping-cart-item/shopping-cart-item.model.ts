import { ShoppingCartAttributeItem } from '../shopping-cart-attribute-item';
import { ShoppingCart } from '../shopping-cart';
export class ShoppingCartItem {
    constructor(
        public id?: number,
        public productId?: number,
        public quantity?: number,
        public attributes?: ShoppingCartAttributeItem,
        public shoppingCart?: ShoppingCart,
    ) {
    }
}
