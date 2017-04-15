import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ContentDescriptionService,
    ContentDescriptionPopupService,
    ContentDescriptionComponent,
    ContentDescriptionDetailComponent,
    ContentDescriptionDialogComponent,
    ContentDescriptionPopupComponent,
    ContentDescriptionDeletePopupComponent,
    ContentDescriptionDeleteDialogComponent,
    contentDescriptionRoute,
    contentDescriptionPopupRoute,
    ContentDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contentDescriptionRoute,
    ...contentDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContentDescriptionComponent,
        ContentDescriptionDetailComponent,
        ContentDescriptionDialogComponent,
        ContentDescriptionDeleteDialogComponent,
        ContentDescriptionPopupComponent,
        ContentDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ContentDescriptionComponent,
        ContentDescriptionDialogComponent,
        ContentDescriptionPopupComponent,
        ContentDescriptionDeleteDialogComponent,
        ContentDescriptionDeletePopupComponent,
    ],
    providers: [
        ContentDescriptionService,
        ContentDescriptionPopupService,
        ContentDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopContentDescriptionModule {}
