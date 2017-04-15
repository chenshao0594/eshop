import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductImageDescriptionService,
    ProductImageDescriptionPopupService,
    ProductImageDescriptionComponent,
    ProductImageDescriptionDetailComponent,
    ProductImageDescriptionDialogComponent,
    ProductImageDescriptionPopupComponent,
    ProductImageDescriptionDeletePopupComponent,
    ProductImageDescriptionDeleteDialogComponent,
    productImageDescriptionRoute,
    productImageDescriptionPopupRoute,
    ProductImageDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productImageDescriptionRoute,
    ...productImageDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductImageDescriptionComponent,
        ProductImageDescriptionDetailComponent,
        ProductImageDescriptionDialogComponent,
        ProductImageDescriptionDeleteDialogComponent,
        ProductImageDescriptionPopupComponent,
        ProductImageDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ProductImageDescriptionComponent,
        ProductImageDescriptionDialogComponent,
        ProductImageDescriptionPopupComponent,
        ProductImageDescriptionDeleteDialogComponent,
        ProductImageDescriptionDeletePopupComponent,
    ],
    providers: [
        ProductImageDescriptionService,
        ProductImageDescriptionPopupService,
        ProductImageDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductImageDescriptionModule {}
