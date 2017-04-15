import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductAvailabilityService,
    ProductAvailabilityPopupService,
    ProductAvailabilityComponent,
    ProductAvailabilityDetailComponent,
    ProductAvailabilityDialogComponent,
    ProductAvailabilityPopupComponent,
    ProductAvailabilityDeletePopupComponent,
    ProductAvailabilityDeleteDialogComponent,
    productAvailabilityRoute,
    productAvailabilityPopupRoute,
    ProductAvailabilityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productAvailabilityRoute,
    ...productAvailabilityPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductAvailabilityComponent,
        ProductAvailabilityDetailComponent,
        ProductAvailabilityDialogComponent,
        ProductAvailabilityDeleteDialogComponent,
        ProductAvailabilityPopupComponent,
        ProductAvailabilityDeletePopupComponent,
    ],
    entryComponents: [
        ProductAvailabilityComponent,
        ProductAvailabilityDialogComponent,
        ProductAvailabilityPopupComponent,
        ProductAvailabilityDeleteDialogComponent,
        ProductAvailabilityDeletePopupComponent,
    ],
    providers: [
        ProductAvailabilityService,
        ProductAvailabilityPopupService,
        ProductAvailabilityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductAvailabilityModule {}
