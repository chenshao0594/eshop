import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    GeoZoneService,
    GeoZonePopupService,
    GeoZoneComponent,
    GeoZoneDetailComponent,
    GeoZoneDialogComponent,
    GeoZonePopupComponent,
    GeoZoneDeletePopupComponent,
    GeoZoneDeleteDialogComponent,
    geoZoneRoute,
    geoZonePopupRoute,
    GeoZoneResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...geoZoneRoute,
    ...geoZonePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GeoZoneComponent,
        GeoZoneDetailComponent,
        GeoZoneDialogComponent,
        GeoZoneDeleteDialogComponent,
        GeoZonePopupComponent,
        GeoZoneDeletePopupComponent,
    ],
    entryComponents: [
        GeoZoneComponent,
        GeoZoneDialogComponent,
        GeoZonePopupComponent,
        GeoZoneDeleteDialogComponent,
        GeoZoneDeletePopupComponent,
    ],
    providers: [
        GeoZoneService,
        GeoZonePopupService,
        GeoZoneResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopGeoZoneModule {}
