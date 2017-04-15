import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CustomerOptionValueService,
    CustomerOptionValuePopupService,
    CustomerOptionValueComponent,
    CustomerOptionValueDetailComponent,
    CustomerOptionValueDialogComponent,
    CustomerOptionValuePopupComponent,
    CustomerOptionValueDeletePopupComponent,
    CustomerOptionValueDeleteDialogComponent,
    customerOptionValueRoute,
    customerOptionValuePopupRoute,
    CustomerOptionValueResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerOptionValueRoute,
    ...customerOptionValuePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerOptionValueComponent,
        CustomerOptionValueDetailComponent,
        CustomerOptionValueDialogComponent,
        CustomerOptionValueDeleteDialogComponent,
        CustomerOptionValuePopupComponent,
        CustomerOptionValueDeletePopupComponent,
    ],
    entryComponents: [
        CustomerOptionValueComponent,
        CustomerOptionValueDialogComponent,
        CustomerOptionValuePopupComponent,
        CustomerOptionValueDeleteDialogComponent,
        CustomerOptionValueDeletePopupComponent,
    ],
    providers: [
        CustomerOptionValueService,
        CustomerOptionValuePopupService,
        CustomerOptionValueResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCustomerOptionValueModule {}
