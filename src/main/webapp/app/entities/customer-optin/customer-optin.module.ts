import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CustomerOptinService,
    CustomerOptinPopupService,
    CustomerOptinComponent,
    CustomerOptinDetailComponent,
    CustomerOptinDialogComponent,
    CustomerOptinPopupComponent,
    CustomerOptinDeletePopupComponent,
    CustomerOptinDeleteDialogComponent,
    customerOptinRoute,
    customerOptinPopupRoute,
    CustomerOptinResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...customerOptinRoute,
    ...customerOptinPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerOptinComponent,
        CustomerOptinDetailComponent,
        CustomerOptinDialogComponent,
        CustomerOptinDeleteDialogComponent,
        CustomerOptinPopupComponent,
        CustomerOptinDeletePopupComponent,
    ],
    entryComponents: [
        CustomerOptinComponent,
        CustomerOptinDialogComponent,
        CustomerOptinPopupComponent,
        CustomerOptinDeleteDialogComponent,
        CustomerOptinDeletePopupComponent,
    ],
    providers: [
        CustomerOptinService,
        CustomerOptinPopupService,
        CustomerOptinResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCustomerOptinModule {}
