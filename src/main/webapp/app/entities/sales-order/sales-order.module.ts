import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    SalesOrderService,
    SalesOrderPopupService,
    SalesOrderComponent,
    SalesOrderDetailComponent,
    SalesOrderDialogComponent,
    SalesOrderPopupComponent,
    SalesOrderDeletePopupComponent,
    SalesOrderDeleteDialogComponent,
    salesOrderRoute,
    salesOrderPopupRoute,
    SalesOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...salesOrderRoute,
    ...salesOrderPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SalesOrderComponent,
        SalesOrderDetailComponent,
        SalesOrderDialogComponent,
        SalesOrderDeleteDialogComponent,
        SalesOrderPopupComponent,
        SalesOrderDeletePopupComponent,
    ],
    entryComponents: [
        SalesOrderComponent,
        SalesOrderDialogComponent,
        SalesOrderPopupComponent,
        SalesOrderDeleteDialogComponent,
        SalesOrderDeletePopupComponent,
    ],
    providers: [
        SalesOrderService,
        SalesOrderPopupService,
        SalesOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopSalesOrderModule {}
