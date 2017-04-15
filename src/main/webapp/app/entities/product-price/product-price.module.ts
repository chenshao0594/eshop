import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductPriceService,
    ProductPricePopupService,
    ProductPriceComponent,
    ProductPriceDetailComponent,
    ProductPriceDialogComponent,
    ProductPricePopupComponent,
    ProductPriceDeletePopupComponent,
    ProductPriceDeleteDialogComponent,
    productPriceRoute,
    productPricePopupRoute,
    ProductPriceResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productPriceRoute,
    ...productPricePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductPriceComponent,
        ProductPriceDetailComponent,
        ProductPriceDialogComponent,
        ProductPriceDeleteDialogComponent,
        ProductPricePopupComponent,
        ProductPriceDeletePopupComponent,
    ],
    entryComponents: [
        ProductPriceComponent,
        ProductPriceDialogComponent,
        ProductPricePopupComponent,
        ProductPriceDeleteDialogComponent,
        ProductPriceDeletePopupComponent,
    ],
    providers: [
        ProductPriceService,
        ProductPricePopupService,
        ProductPriceResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductPriceModule {}
