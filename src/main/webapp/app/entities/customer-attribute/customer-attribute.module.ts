import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CustomerAttributeService,
    CustomerAttributePopupService,
    CustomerAttributeComponent,
    CustomerAttributeDetailComponent,
    CustomerAttributeDialogComponent,
    CustomerAttributePopupComponent,
    CustomerAttributeDeletePopupComponent,
    CustomerAttributeDeleteDialogComponent,
    customerAttributeRoute,
    customerAttributePopupRoute,
    CustomerAttributeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerAttributeRoute,
    ...customerAttributePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerAttributeComponent,
        CustomerAttributeDetailComponent,
        CustomerAttributeDialogComponent,
        CustomerAttributeDeleteDialogComponent,
        CustomerAttributePopupComponent,
        CustomerAttributeDeletePopupComponent,
    ],
    entryComponents: [
        CustomerAttributeComponent,
        CustomerAttributeDialogComponent,
        CustomerAttributePopupComponent,
        CustomerAttributeDeleteDialogComponent,
        CustomerAttributeDeletePopupComponent,
    ],
    providers: [
        CustomerAttributeService,
        CustomerAttributePopupService,
        CustomerAttributeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCustomerAttributeModule {}
