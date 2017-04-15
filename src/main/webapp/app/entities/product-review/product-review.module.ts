import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductReviewService,
    ProductReviewPopupService,
    ProductReviewComponent,
    ProductReviewDetailComponent,
    ProductReviewDialogComponent,
    ProductReviewPopupComponent,
    ProductReviewDeletePopupComponent,
    ProductReviewDeleteDialogComponent,
    productReviewRoute,
    productReviewPopupRoute,
    ProductReviewResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productReviewRoute,
    ...productReviewPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductReviewComponent,
        ProductReviewDetailComponent,
        ProductReviewDialogComponent,
        ProductReviewDeleteDialogComponent,
        ProductReviewPopupComponent,
        ProductReviewDeletePopupComponent,
    ],
    entryComponents: [
        ProductReviewComponent,
        ProductReviewDialogComponent,
        ProductReviewPopupComponent,
        ProductReviewDeleteDialogComponent,
        ProductReviewDeletePopupComponent,
    ],
    providers: [
        ProductReviewService,
        ProductReviewPopupService,
        ProductReviewResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductReviewModule {}
