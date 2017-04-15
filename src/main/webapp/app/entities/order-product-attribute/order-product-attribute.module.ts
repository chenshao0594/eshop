import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OrderProductAttributeService,
    OrderProductAttributePopupService,
    OrderProductAttributeComponent,
    OrderProductAttributeDetailComponent,
    OrderProductAttributeDialogComponent,
    OrderProductAttributePopupComponent,
    OrderProductAttributeDeletePopupComponent,
    OrderProductAttributeDeleteDialogComponent,
    orderProductAttributeRoute,
    orderProductAttributePopupRoute,
    OrderProductAttributeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderProductAttributeRoute,
    ...orderProductAttributePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderProductAttributeComponent,
        OrderProductAttributeDetailComponent,
        OrderProductAttributeDialogComponent,
        OrderProductAttributeDeleteDialogComponent,
        OrderProductAttributePopupComponent,
        OrderProductAttributeDeletePopupComponent,
    ],
    entryComponents: [
        OrderProductAttributeComponent,
        OrderProductAttributeDialogComponent,
        OrderProductAttributePopupComponent,
        OrderProductAttributeDeleteDialogComponent,
        OrderProductAttributeDeletePopupComponent,
    ],
    providers: [
        OrderProductAttributeService,
        OrderProductAttributePopupService,
        OrderProductAttributeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOrderProductAttributeModule {}
