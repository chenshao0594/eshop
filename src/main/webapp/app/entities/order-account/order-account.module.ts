import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OrderAccountService,
    OrderAccountPopupService,
    OrderAccountComponent,
    OrderAccountDetailComponent,
    OrderAccountDialogComponent,
    OrderAccountPopupComponent,
    OrderAccountDeletePopupComponent,
    OrderAccountDeleteDialogComponent,
    orderAccountRoute,
    orderAccountPopupRoute,
    OrderAccountResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderAccountRoute,
    ...orderAccountPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderAccountComponent,
        OrderAccountDetailComponent,
        OrderAccountDialogComponent,
        OrderAccountDeleteDialogComponent,
        OrderAccountPopupComponent,
        OrderAccountDeletePopupComponent,
    ],
    entryComponents: [
        OrderAccountComponent,
        OrderAccountDialogComponent,
        OrderAccountPopupComponent,
        OrderAccountDeleteDialogComponent,
        OrderAccountDeletePopupComponent,
    ],
    providers: [
        OrderAccountService,
        OrderAccountPopupService,
        OrderAccountResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOrderAccountModule {}
