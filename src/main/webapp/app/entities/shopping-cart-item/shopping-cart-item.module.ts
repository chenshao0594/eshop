import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ShoppingCartItemService,
    ShoppingCartItemPopupService,
    ShoppingCartItemComponent,
    ShoppingCartItemDetailComponent,
    ShoppingCartItemDialogComponent,
    ShoppingCartItemPopupComponent,
    ShoppingCartItemDeletePopupComponent,
    ShoppingCartItemDeleteDialogComponent,
    shoppingCartItemRoute,
    shoppingCartItemPopupRoute,
    ShoppingCartItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shoppingCartItemRoute,
    ...shoppingCartItemPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShoppingCartItemComponent,
        ShoppingCartItemDetailComponent,
        ShoppingCartItemDialogComponent,
        ShoppingCartItemDeleteDialogComponent,
        ShoppingCartItemPopupComponent,
        ShoppingCartItemDeletePopupComponent,
    ],
    entryComponents: [
        ShoppingCartItemComponent,
        ShoppingCartItemDialogComponent,
        ShoppingCartItemPopupComponent,
        ShoppingCartItemDeleteDialogComponent,
        ShoppingCartItemDeletePopupComponent,
    ],
    providers: [
        ShoppingCartItemService,
        ShoppingCartItemPopupService,
        ShoppingCartItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopShoppingCartItemModule {}
