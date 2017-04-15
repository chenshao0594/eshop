import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CustomerOptionService,
    CustomerOptionPopupService,
    CustomerOptionComponent,
    CustomerOptionDetailComponent,
    CustomerOptionDialogComponent,
    CustomerOptionPopupComponent,
    CustomerOptionDeletePopupComponent,
    CustomerOptionDeleteDialogComponent,
    customerOptionRoute,
    customerOptionPopupRoute,
    CustomerOptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerOptionRoute,
    ...customerOptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerOptionComponent,
        CustomerOptionDetailComponent,
        CustomerOptionDialogComponent,
        CustomerOptionDeleteDialogComponent,
        CustomerOptionPopupComponent,
        CustomerOptionDeletePopupComponent,
    ],
    entryComponents: [
        CustomerOptionComponent,
        CustomerOptionDialogComponent,
        CustomerOptionPopupComponent,
        CustomerOptionDeleteDialogComponent,
        CustomerOptionDeletePopupComponent,
    ],
    providers: [
        CustomerOptionService,
        CustomerOptionPopupService,
        CustomerOptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCustomerOptionModule {}
