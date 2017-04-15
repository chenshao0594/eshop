import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductOptionValueDescriptionService,
    ProductOptionValueDescriptionPopupService,
    ProductOptionValueDescriptionComponent,
    ProductOptionValueDescriptionDetailComponent,
    ProductOptionValueDescriptionDialogComponent,
    ProductOptionValueDescriptionPopupComponent,
    ProductOptionValueDescriptionDeletePopupComponent,
    ProductOptionValueDescriptionDeleteDialogComponent,
    productOptionValueDescriptionRoute,
    productOptionValueDescriptionPopupRoute,
    ProductOptionValueDescriptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productOptionValueDescriptionRoute,
    ...productOptionValueDescriptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductOptionValueDescriptionComponent,
        ProductOptionValueDescriptionDetailComponent,
        ProductOptionValueDescriptionDialogComponent,
        ProductOptionValueDescriptionDeleteDialogComponent,
        ProductOptionValueDescriptionPopupComponent,
        ProductOptionValueDescriptionDeletePopupComponent,
    ],
    entryComponents: [
        ProductOptionValueDescriptionComponent,
        ProductOptionValueDescriptionDialogComponent,
        ProductOptionValueDescriptionPopupComponent,
        ProductOptionValueDescriptionDeleteDialogComponent,
        ProductOptionValueDescriptionDeletePopupComponent,
    ],
    providers: [
        ProductOptionValueDescriptionService,
        ProductOptionValueDescriptionPopupService,
        ProductOptionValueDescriptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductOptionValueDescriptionModule {}
