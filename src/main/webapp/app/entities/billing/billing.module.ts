import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    BillingService,
    BillingPopupService,
    BillingComponent,
    BillingDetailComponent,
    BillingDialogComponent,
    BillingPopupComponent,
    BillingDeletePopupComponent,
    BillingDeleteDialogComponent,
    billingRoute,
    billingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...billingRoute,
    ...billingPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BillingComponent,
        BillingDetailComponent,
        BillingDialogComponent,
        BillingDeleteDialogComponent,
        BillingPopupComponent,
        BillingDeletePopupComponent,
    ],
    entryComponents: [
        BillingComponent,
        BillingDialogComponent,
        BillingPopupComponent,
        BillingDeleteDialogComponent,
        BillingDeletePopupComponent,
    ],
    providers: [
        BillingService,
        BillingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopBillingModule {}
