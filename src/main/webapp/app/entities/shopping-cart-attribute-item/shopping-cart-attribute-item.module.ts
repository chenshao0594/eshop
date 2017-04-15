import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ShoppingCartAttributeItemService,
    ShoppingCartAttributeItemPopupService,
    ShoppingCartAttributeItemComponent,
    ShoppingCartAttributeItemDetailComponent,
    ShoppingCartAttributeItemDialogComponent,
    ShoppingCartAttributeItemPopupComponent,
    ShoppingCartAttributeItemDeletePopupComponent,
    ShoppingCartAttributeItemDeleteDialogComponent,
    shoppingCartAttributeItemRoute,
    shoppingCartAttributeItemPopupRoute,
    ShoppingCartAttributeItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shoppingCartAttributeItemRoute,
    ...shoppingCartAttributeItemPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShoppingCartAttributeItemComponent,
        ShoppingCartAttributeItemDetailComponent,
        ShoppingCartAttributeItemDialogComponent,
        ShoppingCartAttributeItemDeleteDialogComponent,
        ShoppingCartAttributeItemPopupComponent,
        ShoppingCartAttributeItemDeletePopupComponent,
    ],
    entryComponents: [
        ShoppingCartAttributeItemComponent,
        ShoppingCartAttributeItemDialogComponent,
        ShoppingCartAttributeItemPopupComponent,
        ShoppingCartAttributeItemDeleteDialogComponent,
        ShoppingCartAttributeItemDeletePopupComponent,
    ],
    providers: [
        ShoppingCartAttributeItemService,
        ShoppingCartAttributeItemPopupService,
        ShoppingCartAttributeItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopShoppingCartAttributeItemModule {}
