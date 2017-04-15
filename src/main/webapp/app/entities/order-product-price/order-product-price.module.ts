import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OrderProductPriceService,
    OrderProductPricePopupService,
    OrderProductPriceComponent,
    OrderProductPriceDetailComponent,
    OrderProductPriceDialogComponent,
    OrderProductPricePopupComponent,
    OrderProductPriceDeletePopupComponent,
    OrderProductPriceDeleteDialogComponent,
    orderProductPriceRoute,
    orderProductPricePopupRoute,
    OrderProductPriceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderProductPriceRoute,
    ...orderProductPricePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderProductPriceComponent,
        OrderProductPriceDetailComponent,
        OrderProductPriceDialogComponent,
        OrderProductPriceDeleteDialogComponent,
        OrderProductPricePopupComponent,
        OrderProductPriceDeletePopupComponent,
    ],
    entryComponents: [
        OrderProductPriceComponent,
        OrderProductPriceDialogComponent,
        OrderProductPricePopupComponent,
        OrderProductPriceDeleteDialogComponent,
        OrderProductPriceDeletePopupComponent,
    ],
    providers: [
        OrderProductPriceService,
        OrderProductPricePopupService,
        OrderProductPriceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOrderProductPriceModule {}
