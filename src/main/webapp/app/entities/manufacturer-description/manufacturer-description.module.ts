import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ManufacturerDescriptionService,
    ManufacturerDescriptionPopupService,
    ManufacturerDescriptionComponent,
    ManufacturerDescriptionDetailComponent,
    ManufacturerDescriptionDialogComponent,
    ManufacturerDescriptionPopupComponent,
    ManufacturerDescriptionDeletePopupComponent,
    ManufacturerDescriptionDeleteDialogComponent,
    manufacturerDescriptionRoute,
    manufacturerDescriptionPopupRoute,
    ManufacturerDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...manufacturerDescriptionRoute,
    ...manufacturerDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ManufacturerDescriptionComponent,
        ManufacturerDescriptionDetailComponent,
        ManufacturerDescriptionDialogComponent,
        ManufacturerDescriptionDeleteDialogComponent,
        ManufacturerDescriptionPopupComponent,
        ManufacturerDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ManufacturerDescriptionComponent,
        ManufacturerDescriptionDialogComponent,
        ManufacturerDescriptionPopupComponent,
        ManufacturerDescriptionDeleteDialogComponent,
        ManufacturerDescriptionDeletePopupComponent,
    ],
    providers: [
        ManufacturerDescriptionService,
        ManufacturerDescriptionPopupService,
        ManufacturerDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopManufacturerDescriptionModule {}
