import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductOptionDescriptionService,
    ProductOptionDescriptionPopupService,
    ProductOptionDescriptionComponent,
    ProductOptionDescriptionDetailComponent,
    ProductOptionDescriptionDialogComponent,
    ProductOptionDescriptionPopupComponent,
    ProductOptionDescriptionDeletePopupComponent,
    ProductOptionDescriptionDeleteDialogComponent,
    productOptionDescriptionRoute,
    productOptionDescriptionPopupRoute,
    ProductOptionDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productOptionDescriptionRoute,
    ...productOptionDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductOptionDescriptionComponent,
        ProductOptionDescriptionDetailComponent,
        ProductOptionDescriptionDialogComponent,
        ProductOptionDescriptionDeleteDialogComponent,
        ProductOptionDescriptionPopupComponent,
        ProductOptionDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ProductOptionDescriptionComponent,
        ProductOptionDescriptionDialogComponent,
        ProductOptionDescriptionPopupComponent,
        ProductOptionDescriptionDeleteDialogComponent,
        ProductOptionDescriptionDeletePopupComponent,
    ],
    providers: [
        ProductOptionDescriptionService,
        ProductOptionDescriptionPopupService,
        ProductOptionDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductOptionDescriptionModule {}
