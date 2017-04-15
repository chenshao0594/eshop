import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductPriceDescriptionService,
    ProductPriceDescriptionPopupService,
    ProductPriceDescriptionComponent,
    ProductPriceDescriptionDetailComponent,
    ProductPriceDescriptionDialogComponent,
    ProductPriceDescriptionPopupComponent,
    ProductPriceDescriptionDeletePopupComponent,
    ProductPriceDescriptionDeleteDialogComponent,
    productPriceDescriptionRoute,
    productPriceDescriptionPopupRoute,
    ProductPriceDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productPriceDescriptionRoute,
    ...productPriceDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductPriceDescriptionComponent,
        ProductPriceDescriptionDetailComponent,
        ProductPriceDescriptionDialogComponent,
        ProductPriceDescriptionDeleteDialogComponent,
        ProductPriceDescriptionPopupComponent,
        ProductPriceDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ProductPriceDescriptionComponent,
        ProductPriceDescriptionDialogComponent,
        ProductPriceDescriptionPopupComponent,
        ProductPriceDescriptionDeleteDialogComponent,
        ProductPriceDescriptionDeletePopupComponent,
    ],
    providers: [
        ProductPriceDescriptionService,
        ProductPriceDescriptionPopupService,
        ProductPriceDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductPriceDescriptionModule {}
