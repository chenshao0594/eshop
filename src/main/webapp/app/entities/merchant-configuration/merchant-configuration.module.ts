import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    MerchantConfigurationService,
    MerchantConfigurationPopupService,
    MerchantConfigurationComponent,
    MerchantConfigurationDetailComponent,
    MerchantConfigurationDialogComponent,
    MerchantConfigurationPopupComponent,
    MerchantConfigurationDeletePopupComponent,
    MerchantConfigurationDeleteDialogComponent,
    merchantConfigurationRoute,
    merchantConfigurationPopupRoute,
    MerchantConfigurationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...merchantConfigurationRoute,
    ...merchantConfigurationPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MerchantConfigurationComponent,
        MerchantConfigurationDetailComponent,
        MerchantConfigurationDialogComponent,
        MerchantConfigurationDeleteDialogComponent,
        MerchantConfigurationPopupComponent,
        MerchantConfigurationDeletePopupComponent,
    ],
    entryComponents: [
        MerchantConfigurationComponent,
        MerchantConfigurationDialogComponent,
        MerchantConfigurationPopupComponent,
        MerchantConfigurationDeleteDialogComponent,
        MerchantConfigurationDeletePopupComponent,
    ],
    providers: [
        MerchantConfigurationService,
        MerchantConfigurationPopupService,
        MerchantConfigurationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopMerchantConfigurationModule {}
