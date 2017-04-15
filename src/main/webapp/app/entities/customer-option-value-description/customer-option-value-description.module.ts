import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CustomerOptionValueDescriptionService,
    CustomerOptionValueDescriptionPopupService,
    CustomerOptionValueDescriptionComponent,
    CustomerOptionValueDescriptionDetailComponent,
    CustomerOptionValueDescriptionDialogComponent,
    CustomerOptionValueDescriptionPopupComponent,
    CustomerOptionValueDescriptionDeletePopupComponent,
    CustomerOptionValueDescriptionDeleteDialogComponent,
    customerOptionValueDescriptionRoute,
    customerOptionValueDescriptionPopupRoute,
    CustomerOptionValueDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerOptionValueDescriptionRoute,
    ...customerOptionValueDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerOptionValueDescriptionComponent,
        CustomerOptionValueDescriptionDetailComponent,
        CustomerOptionValueDescriptionDialogComponent,
        CustomerOptionValueDescriptionDeleteDialogComponent,
        CustomerOptionValueDescriptionPopupComponent,
        CustomerOptionValueDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        CustomerOptionValueDescriptionComponent,
        CustomerOptionValueDescriptionDialogComponent,
        CustomerOptionValueDescriptionPopupComponent,
        CustomerOptionValueDescriptionDeleteDialogComponent,
        CustomerOptionValueDescriptionDeletePopupComponent,
    ],
    providers: [
        CustomerOptionValueDescriptionService,
        CustomerOptionValueDescriptionPopupService,
        CustomerOptionValueDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCustomerOptionValueDescriptionModule {}
