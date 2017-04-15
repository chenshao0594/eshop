import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CustomerOptionSetService,
    CustomerOptionSetPopupService,
    CustomerOptionSetComponent,
    CustomerOptionSetDetailComponent,
    CustomerOptionSetDialogComponent,
    CustomerOptionSetPopupComponent,
    CustomerOptionSetDeletePopupComponent,
    CustomerOptionSetDeleteDialogComponent,
    customerOptionSetRoute,
    customerOptionSetPopupRoute,
    CustomerOptionSetResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerOptionSetRoute,
    ...customerOptionSetPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerOptionSetComponent,
        CustomerOptionSetDetailComponent,
        CustomerOptionSetDialogComponent,
        CustomerOptionSetDeleteDialogComponent,
        CustomerOptionSetPopupComponent,
        CustomerOptionSetDeletePopupComponent,
    ],
    entryComponents: [
        CustomerOptionSetComponent,
        CustomerOptionSetDialogComponent,
        CustomerOptionSetPopupComponent,
        CustomerOptionSetDeleteDialogComponent,
        CustomerOptionSetDeletePopupComponent,
    ],
    providers: [
        CustomerOptionSetService,
        CustomerOptionSetPopupService,
        CustomerOptionSetResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCustomerOptionSetModule {}
