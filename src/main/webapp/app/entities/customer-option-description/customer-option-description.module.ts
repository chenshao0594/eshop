import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CustomerOptionDescriptionService,
    CustomerOptionDescriptionPopupService,
    CustomerOptionDescriptionComponent,
    CustomerOptionDescriptionDetailComponent,
    CustomerOptionDescriptionDialogComponent,
    CustomerOptionDescriptionPopupComponent,
    CustomerOptionDescriptionDeletePopupComponent,
    CustomerOptionDescriptionDeleteDialogComponent,
    customerOptionDescriptionRoute,
    customerOptionDescriptionPopupRoute,
    CustomerOptionDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerOptionDescriptionRoute,
    ...customerOptionDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerOptionDescriptionComponent,
        CustomerOptionDescriptionDetailComponent,
        CustomerOptionDescriptionDialogComponent,
        CustomerOptionDescriptionDeleteDialogComponent,
        CustomerOptionDescriptionPopupComponent,
        CustomerOptionDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        CustomerOptionDescriptionComponent,
        CustomerOptionDescriptionDialogComponent,
        CustomerOptionDescriptionPopupComponent,
        CustomerOptionDescriptionDeleteDialogComponent,
        CustomerOptionDescriptionDeletePopupComponent,
    ],
    providers: [
        CustomerOptionDescriptionService,
        CustomerOptionDescriptionPopupService,
        CustomerOptionDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCustomerOptionDescriptionModule {}
