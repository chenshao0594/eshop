import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    IntegrationModuleService,
    IntegrationModulePopupService,
    IntegrationModuleComponent,
    IntegrationModuleDetailComponent,
    IntegrationModuleDialogComponent,
    IntegrationModulePopupComponent,
    IntegrationModuleDeletePopupComponent,
    IntegrationModuleDeleteDialogComponent,
    integrationModuleRoute,
    integrationModulePopupRoute,
    IntegrationModuleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...integrationModuleRoute,
    ...integrationModulePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IntegrationModuleComponent,
        IntegrationModuleDetailComponent,
        IntegrationModuleDialogComponent,
        IntegrationModuleDeleteDialogComponent,
        IntegrationModulePopupComponent,
        IntegrationModuleDeletePopupComponent,
    ],
    entryComponents: [
        IntegrationModuleComponent,
        IntegrationModuleDialogComponent,
        IntegrationModulePopupComponent,
        IntegrationModuleDeleteDialogComponent,
        IntegrationModuleDeletePopupComponent,
    ],
    providers: [
        IntegrationModuleService,
        IntegrationModulePopupService,
        IntegrationModuleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopIntegrationModuleModule {}
