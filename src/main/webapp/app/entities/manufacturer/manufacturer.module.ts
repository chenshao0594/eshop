import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ManufacturerService,
    ManufacturerPopupService,
    ManufacturerComponent,
    ManufacturerDetailComponent,
    ManufacturerDialogComponent,
    ManufacturerPopupComponent,
    ManufacturerDeletePopupComponent,
    ManufacturerDeleteDialogComponent,
    manufacturerRoute,
    manufacturerPopupRoute,
    ManufacturerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...manufacturerRoute,
    ...manufacturerPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ManufacturerComponent,
        ManufacturerDetailComponent,
        ManufacturerDialogComponent,
        ManufacturerDeleteDialogComponent,
        ManufacturerPopupComponent,
        ManufacturerDeletePopupComponent,
    ],
    entryComponents: [
        ManufacturerComponent,
        ManufacturerDialogComponent,
        ManufacturerPopupComponent,
        ManufacturerDeleteDialogComponent,
        ManufacturerDeletePopupComponent,
    ],
    providers: [
        ManufacturerService,
        ManufacturerPopupService,
        ManufacturerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopManufacturerModule {}
