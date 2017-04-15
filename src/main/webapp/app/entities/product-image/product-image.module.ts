import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductImageService,
    ProductImagePopupService,
    ProductImageComponent,
    ProductImageDetailComponent,
    ProductImageDialogComponent,
    ProductImagePopupComponent,
    ProductImageDeletePopupComponent,
    ProductImageDeleteDialogComponent,
    productImageRoute,
    productImagePopupRoute,
    ProductImageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productImageRoute,
    ...productImagePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductImageComponent,
        ProductImageDetailComponent,
        ProductImageDialogComponent,
        ProductImageDeleteDialogComponent,
        ProductImagePopupComponent,
        ProductImageDeletePopupComponent,
    ],
    entryComponents: [
        ProductImageComponent,
        ProductImageDialogComponent,
        ProductImagePopupComponent,
        ProductImageDeleteDialogComponent,
        ProductImageDeletePopupComponent,
    ],
    providers: [
        ProductImageService,
        ProductImagePopupService,
        ProductImageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductImageModule {}
