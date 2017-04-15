import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    TaxClassService,
    TaxClassPopupService,
    TaxClassComponent,
    TaxClassDetailComponent,
    TaxClassDialogComponent,
    TaxClassPopupComponent,
    TaxClassDeletePopupComponent,
    TaxClassDeleteDialogComponent,
    taxClassRoute,
    taxClassPopupRoute,
    TaxClassResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...taxClassRoute,
    ...taxClassPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TaxClassComponent,
        TaxClassDetailComponent,
        TaxClassDialogComponent,
        TaxClassDeleteDialogComponent,
        TaxClassPopupComponent,
        TaxClassDeletePopupComponent,
    ],
    entryComponents: [
        TaxClassComponent,
        TaxClassDialogComponent,
        TaxClassPopupComponent,
        TaxClassDeleteDialogComponent,
        TaxClassDeletePopupComponent,
    ],
    providers: [
        TaxClassService,
        TaxClassPopupService,
        TaxClassResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopTaxClassModule {}
