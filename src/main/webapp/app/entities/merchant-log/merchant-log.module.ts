import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    MerchantLogService,
    MerchantLogPopupService,
    MerchantLogComponent,
    MerchantLogDetailComponent,
    MerchantLogDialogComponent,
    MerchantLogPopupComponent,
    MerchantLogDeletePopupComponent,
    MerchantLogDeleteDialogComponent,
    merchantLogRoute,
    merchantLogPopupRoute,
    MerchantLogResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...merchantLogRoute,
    ...merchantLogPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MerchantLogComponent,
        MerchantLogDetailComponent,
        MerchantLogDialogComponent,
        MerchantLogDeleteDialogComponent,
        MerchantLogPopupComponent,
        MerchantLogDeletePopupComponent,
    ],
    entryComponents: [
        MerchantLogComponent,
        MerchantLogDialogComponent,
        MerchantLogPopupComponent,
        MerchantLogDeleteDialogComponent,
        MerchantLogDeletePopupComponent,
    ],
    providers: [
        MerchantLogService,
        MerchantLogPopupService,
        MerchantLogResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopMerchantLogModule {}
