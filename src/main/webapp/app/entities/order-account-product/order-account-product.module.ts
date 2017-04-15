import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OrderAccountProductService,
    OrderAccountProductPopupService,
    OrderAccountProductComponent,
    OrderAccountProductDetailComponent,
    OrderAccountProductDialogComponent,
    OrderAccountProductPopupComponent,
    OrderAccountProductDeletePopupComponent,
    OrderAccountProductDeleteDialogComponent,
    orderAccountProductRoute,
    orderAccountProductPopupRoute,
    OrderAccountProductResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderAccountProductRoute,
    ...orderAccountProductPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderAccountProductComponent,
        OrderAccountProductDetailComponent,
        OrderAccountProductDialogComponent,
        OrderAccountProductDeleteDialogComponent,
        OrderAccountProductPopupComponent,
        OrderAccountProductDeletePopupComponent,
    ],
    entryComponents: [
        OrderAccountProductComponent,
        OrderAccountProductDialogComponent,
        OrderAccountProductPopupComponent,
        OrderAccountProductDeleteDialogComponent,
        OrderAccountProductDeletePopupComponent,
    ],
    providers: [
        OrderAccountProductService,
        OrderAccountProductPopupService,
        OrderAccountProductResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOrderAccountProductModule {}
