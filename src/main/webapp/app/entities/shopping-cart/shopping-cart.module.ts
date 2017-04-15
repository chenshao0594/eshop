import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ShoppingCartService,
    ShoppingCartPopupService,
    ShoppingCartComponent,
    ShoppingCartDetailComponent,
    ShoppingCartDialogComponent,
    ShoppingCartPopupComponent,
    ShoppingCartDeletePopupComponent,
    ShoppingCartDeleteDialogComponent,
    shoppingCartRoute,
    shoppingCartPopupRoute,
    ShoppingCartResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shoppingCartRoute,
    ...shoppingCartPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShoppingCartComponent,
        ShoppingCartDetailComponent,
        ShoppingCartDialogComponent,
        ShoppingCartDeleteDialogComponent,
        ShoppingCartPopupComponent,
        ShoppingCartDeletePopupComponent,
    ],
    entryComponents: [
        ShoppingCartComponent,
        ShoppingCartDialogComponent,
        ShoppingCartPopupComponent,
        ShoppingCartDeleteDialogComponent,
        ShoppingCartDeletePopupComponent,
    ],
    providers: [
        ShoppingCartService,
        ShoppingCartPopupService,
        ShoppingCartResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopShoppingCartModule {}
