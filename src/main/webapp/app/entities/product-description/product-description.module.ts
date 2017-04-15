import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductDescriptionService,
    ProductDescriptionPopupService,
    ProductDescriptionComponent,
    ProductDescriptionDetailComponent,
    ProductDescriptionDialogComponent,
    ProductDescriptionPopupComponent,
    ProductDescriptionDeletePopupComponent,
    ProductDescriptionDeleteDialogComponent,
    productDescriptionRoute,
    productDescriptionPopupRoute,
    ProductDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productDescriptionRoute,
    ...productDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductDescriptionComponent,
        ProductDescriptionDetailComponent,
        ProductDescriptionDialogComponent,
        ProductDescriptionDeleteDialogComponent,
        ProductDescriptionPopupComponent,
        ProductDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ProductDescriptionComponent,
        ProductDescriptionDialogComponent,
        ProductDescriptionPopupComponent,
        ProductDescriptionDeleteDialogComponent,
        ProductDescriptionDeletePopupComponent,
    ],
    providers: [
        ProductDescriptionService,
        ProductDescriptionPopupService,
        ProductDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductDescriptionModule {}
