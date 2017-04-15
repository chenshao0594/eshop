import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    GeoZoneDescriptionService,
    GeoZoneDescriptionPopupService,
    GeoZoneDescriptionComponent,
    GeoZoneDescriptionDetailComponent,
    GeoZoneDescriptionDialogComponent,
    GeoZoneDescriptionPopupComponent,
    GeoZoneDescriptionDeletePopupComponent,
    GeoZoneDescriptionDeleteDialogComponent,
    geoZoneDescriptionRoute,
    geoZoneDescriptionPopupRoute,
    GeoZoneDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...geoZoneDescriptionRoute,
    ...geoZoneDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GeoZoneDescriptionComponent,
        GeoZoneDescriptionDetailComponent,
        GeoZoneDescriptionDialogComponent,
        GeoZoneDescriptionDeleteDialogComponent,
        GeoZoneDescriptionPopupComponent,
        GeoZoneDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        GeoZoneDescriptionComponent,
        GeoZoneDescriptionDialogComponent,
        GeoZoneDescriptionPopupComponent,
        GeoZoneDescriptionDeleteDialogComponent,
        GeoZoneDescriptionDeletePopupComponent,
    ],
    providers: [
        GeoZoneDescriptionService,
        GeoZoneDescriptionPopupService,
        GeoZoneDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopGeoZoneDescriptionModule {}
