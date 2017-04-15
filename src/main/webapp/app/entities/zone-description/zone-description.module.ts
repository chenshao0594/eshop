import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ZoneDescriptionService,
    ZoneDescriptionPopupService,
    ZoneDescriptionComponent,
    ZoneDescriptionDetailComponent,
    ZoneDescriptionDialogComponent,
    ZoneDescriptionPopupComponent,
    ZoneDescriptionDeletePopupComponent,
    ZoneDescriptionDeleteDialogComponent,
    zoneDescriptionRoute,
    zoneDescriptionPopupRoute,
    ZoneDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...zoneDescriptionRoute,
    ...zoneDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ZoneDescriptionComponent,
        ZoneDescriptionDetailComponent,
        ZoneDescriptionDialogComponent,
        ZoneDescriptionDeleteDialogComponent,
        ZoneDescriptionPopupComponent,
        ZoneDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ZoneDescriptionComponent,
        ZoneDescriptionDialogComponent,
        ZoneDescriptionPopupComponent,
        ZoneDescriptionDeleteDialogComponent,
        ZoneDescriptionDeletePopupComponent,
    ],
    providers: [
        ZoneDescriptionService,
        ZoneDescriptionPopupService,
        ZoneDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopZoneDescriptionModule {}
