import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    LanguageService,
    LanguagePopupService,
    LanguageComponent,
    LanguageDetailComponent,
    LanguageDialogComponent,
    LanguagePopupComponent,
    LanguageDeletePopupComponent,
    LanguageDeleteDialogComponent,
    languageRoute,
    languagePopupRoute,
    LanguageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...languageRoute,
    ...languagePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LanguageComponent,
        LanguageDetailComponent,
        LanguageDialogComponent,
        LanguageDeleteDialogComponent,
        LanguagePopupComponent,
        LanguageDeletePopupComponent,
    ],
    entryComponents: [
        LanguageComponent,
        LanguageDialogComponent,
        LanguagePopupComponent,
        LanguageDeleteDialogComponent,
        LanguageDeletePopupComponent,
    ],
    providers: [
        LanguageService,
        LanguagePopupService,
        LanguageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopLanguageModule {}
