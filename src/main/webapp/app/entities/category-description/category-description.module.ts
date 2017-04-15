import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    CategoryDescriptionService,
    CategoryDescriptionPopupService,
    CategoryDescriptionComponent,
    CategoryDescriptionDetailComponent,
    CategoryDescriptionDialogComponent,
    CategoryDescriptionPopupComponent,
    CategoryDescriptionDeletePopupComponent,
    CategoryDescriptionDeleteDialogComponent,
    categoryDescriptionRoute,
    categoryDescriptionPopupRoute,
    CategoryDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...categoryDescriptionRoute,
    ...categoryDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CategoryDescriptionComponent,
        CategoryDescriptionDetailComponent,
        CategoryDescriptionDialogComponent,
        CategoryDescriptionDeleteDialogComponent,
        CategoryDescriptionPopupComponent,
        CategoryDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        CategoryDescriptionComponent,
        CategoryDescriptionDialogComponent,
        CategoryDescriptionPopupComponent,
        CategoryDescriptionDeleteDialogComponent,
        CategoryDescriptionDeletePopupComponent,
    ],
    providers: [
        CategoryDescriptionService,
        CategoryDescriptionPopupService,
        CategoryDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopCategoryDescriptionModule {}
