import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    TaxRateDescriptionService,
    TaxRateDescriptionPopupService,
    TaxRateDescriptionComponent,
    TaxRateDescriptionDetailComponent,
    TaxRateDescriptionDialogComponent,
    TaxRateDescriptionPopupComponent,
    TaxRateDescriptionDeletePopupComponent,
    TaxRateDescriptionDeleteDialogComponent,
    taxRateDescriptionRoute,
    taxRateDescriptionPopupRoute,
    TaxRateDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...taxRateDescriptionRoute,
    ...taxRateDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TaxRateDescriptionComponent,
        TaxRateDescriptionDetailComponent,
        TaxRateDescriptionDialogComponent,
        TaxRateDescriptionDeleteDialogComponent,
        TaxRateDescriptionPopupComponent,
        TaxRateDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        TaxRateDescriptionComponent,
        TaxRateDescriptionDialogComponent,
        TaxRateDescriptionPopupComponent,
        TaxRateDescriptionDeleteDialogComponent,
        TaxRateDescriptionDeletePopupComponent,
    ],
    providers: [
        TaxRateDescriptionService,
        TaxRateDescriptionPopupService,
        TaxRateDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopTaxRateDescriptionModule {}
