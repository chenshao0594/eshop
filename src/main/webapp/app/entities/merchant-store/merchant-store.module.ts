import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    MerchantStoreService,
    MerchantStorePopupService,
    MerchantStoreComponent,
    MerchantStoreDetailComponent,
    MerchantStoreDialogComponent,
    MerchantStorePopupComponent,
    MerchantStoreDeletePopupComponent,
    MerchantStoreDeleteDialogComponent,
    merchantStoreRoute,
    merchantStorePopupRoute,
    MerchantStoreResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...merchantStoreRoute,
    ...merchantStorePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MerchantStoreComponent,
        MerchantStoreDetailComponent,
        MerchantStoreDialogComponent,
        MerchantStoreDeleteDialogComponent,
        MerchantStorePopupComponent,
        MerchantStoreDeletePopupComponent,
    ],
    entryComponents: [
        MerchantStoreComponent,
        MerchantStoreDialogComponent,
        MerchantStorePopupComponent,
        MerchantStoreDeleteDialogComponent,
        MerchantStoreDeletePopupComponent,
    ],
    providers: [
        MerchantStoreService,
        MerchantStorePopupService,
        MerchantStoreResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopMerchantStoreModule {}
