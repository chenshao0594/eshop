import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OrderTotalService,
    OrderTotalPopupService,
    OrderTotalComponent,
    OrderTotalDetailComponent,
    OrderTotalDialogComponent,
    OrderTotalPopupComponent,
    OrderTotalDeletePopupComponent,
    OrderTotalDeleteDialogComponent,
    orderTotalRoute,
    orderTotalPopupRoute,
    OrderTotalResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderTotalRoute,
    ...orderTotalPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderTotalComponent,
        OrderTotalDetailComponent,
        OrderTotalDialogComponent,
        OrderTotalDeleteDialogComponent,
        OrderTotalPopupComponent,
        OrderTotalDeletePopupComponent,
    ],
    entryComponents: [
        OrderTotalComponent,
        OrderTotalDialogComponent,
        OrderTotalPopupComponent,
        OrderTotalDeleteDialogComponent,
        OrderTotalDeletePopupComponent,
    ],
    providers: [
        OrderTotalService,
        OrderTotalPopupService,
        OrderTotalResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOrderTotalModule {}
