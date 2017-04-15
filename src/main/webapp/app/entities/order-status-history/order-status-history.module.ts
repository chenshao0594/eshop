import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OrderStatusHistoryService,
    OrderStatusHistoryPopupService,
    OrderStatusHistoryComponent,
    OrderStatusHistoryDetailComponent,
    OrderStatusHistoryDialogComponent,
    OrderStatusHistoryPopupComponent,
    OrderStatusHistoryDeletePopupComponent,
    OrderStatusHistoryDeleteDialogComponent,
    orderStatusHistoryRoute,
    orderStatusHistoryPopupRoute,
    OrderStatusHistoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderStatusHistoryRoute,
    ...orderStatusHistoryPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderStatusHistoryComponent,
        OrderStatusHistoryDetailComponent,
        OrderStatusHistoryDialogComponent,
        OrderStatusHistoryDeleteDialogComponent,
        OrderStatusHistoryPopupComponent,
        OrderStatusHistoryDeletePopupComponent,
    ],
    entryComponents: [
        OrderStatusHistoryComponent,
        OrderStatusHistoryDialogComponent,
        OrderStatusHistoryPopupComponent,
        OrderStatusHistoryDeleteDialogComponent,
        OrderStatusHistoryDeletePopupComponent,
    ],
    providers: [
        OrderStatusHistoryService,
        OrderStatusHistoryPopupService,
        OrderStatusHistoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOrderStatusHistoryModule {}
