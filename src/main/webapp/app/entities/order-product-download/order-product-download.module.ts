import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OrderProductDownloadService,
    OrderProductDownloadPopupService,
    OrderProductDownloadComponent,
    OrderProductDownloadDetailComponent,
    OrderProductDownloadDialogComponent,
    OrderProductDownloadPopupComponent,
    OrderProductDownloadDeletePopupComponent,
    OrderProductDownloadDeleteDialogComponent,
    orderProductDownloadRoute,
    orderProductDownloadPopupRoute,
    OrderProductDownloadResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderProductDownloadRoute,
    ...orderProductDownloadPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderProductDownloadComponent,
        OrderProductDownloadDetailComponent,
        OrderProductDownloadDialogComponent,
        OrderProductDownloadDeleteDialogComponent,
        OrderProductDownloadPopupComponent,
        OrderProductDownloadDeletePopupComponent,
    ],
    entryComponents: [
        OrderProductDownloadComponent,
        OrderProductDownloadDialogComponent,
        OrderProductDownloadPopupComponent,
        OrderProductDownloadDeleteDialogComponent,
        OrderProductDownloadDeletePopupComponent,
    ],
    providers: [
        OrderProductDownloadService,
        OrderProductDownloadPopupService,
        OrderProductDownloadResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOrderProductDownloadModule {}
