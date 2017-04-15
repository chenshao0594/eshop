import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CountryDescriptionService,
    CountryDescriptionPopupService,
    CountryDescriptionComponent,
    CountryDescriptionDetailComponent,
    CountryDescriptionDialogComponent,
    CountryDescriptionPopupComponent,
    CountryDescriptionDeletePopupComponent,
    CountryDescriptionDeleteDialogComponent,
    countryDescriptionRoute,
    countryDescriptionPopupRoute,
    CountryDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...countryDescriptionRoute,
    ...countryDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CountryDescriptionComponent,
        CountryDescriptionDetailComponent,
        CountryDescriptionDialogComponent,
        CountryDescriptionDeleteDialogComponent,
        CountryDescriptionPopupComponent,
        CountryDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        CountryDescriptionComponent,
        CountryDescriptionDialogComponent,
        CountryDescriptionPopupComponent,
        CountryDescriptionDeleteDialogComponent,
        CountryDescriptionDeletePopupComponent,
    ],
    providers: [
        CountryDescriptionService,
        CountryDescriptionPopupService,
        CountryDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCountryDescriptionModule {}
