import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    TaxRateService,
    TaxRatePopupService,
    TaxRateComponent,
    TaxRateDetailComponent,
    TaxRateDialogComponent,
    TaxRatePopupComponent,
    TaxRateDeletePopupComponent,
    TaxRateDeleteDialogComponent,
    taxRateRoute,
    taxRatePopupRoute,
    TaxRateResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...taxRateRoute,
    ...taxRatePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TaxRateComponent,
        TaxRateDetailComponent,
        TaxRateDialogComponent,
        TaxRateDeleteDialogComponent,
        TaxRatePopupComponent,
        TaxRateDeletePopupComponent,
    ],
    entryComponents: [
        TaxRateComponent,
        TaxRateDialogComponent,
        TaxRatePopupComponent,
        TaxRateDeleteDialogComponent,
        TaxRateDeletePopupComponent,
    ],
    providers: [
        TaxRateService,
        TaxRatePopupService,
        TaxRateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopTaxRateModule {}
