import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductReviewDescriptionService,
    ProductReviewDescriptionPopupService,
    ProductReviewDescriptionComponent,
    ProductReviewDescriptionDetailComponent,
    ProductReviewDescriptionDialogComponent,
    ProductReviewDescriptionPopupComponent,
    ProductReviewDescriptionDeletePopupComponent,
    ProductReviewDescriptionDeleteDialogComponent,
    productReviewDescriptionRoute,
    productReviewDescriptionPopupRoute,
    ProductReviewDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productReviewDescriptionRoute,
    ...productReviewDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductReviewDescriptionComponent,
        ProductReviewDescriptionDetailComponent,
        ProductReviewDescriptionDialogComponent,
        ProductReviewDescriptionDeleteDialogComponent,
        ProductReviewDescriptionPopupComponent,
        ProductReviewDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ProductReviewDescriptionComponent,
        ProductReviewDescriptionDialogComponent,
        ProductReviewDescriptionPopupComponent,
        ProductReviewDescriptionDeleteDialogComponent,
        ProductReviewDescriptionDeletePopupComponent,
    ],
    providers: [
        ProductReviewDescriptionService,
        ProductReviewDescriptionPopupService,
        ProductReviewDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductReviewDescriptionModule {}
