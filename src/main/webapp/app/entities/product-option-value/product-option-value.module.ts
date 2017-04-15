import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductOptionValueService,
    ProductOptionValuePopupService,
    ProductOptionValueComponent,
    ProductOptionValueDetailComponent,
    ProductOptionValueDialogComponent,
    ProductOptionValuePopupComponent,
    ProductOptionValueDeletePopupComponent,
    ProductOptionValueDeleteDialogComponent,
    productOptionValueRoute,
    productOptionValuePopupRoute,
    ProductOptionValueResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productOptionValueRoute,
    ...productOptionValuePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductOptionValueComponent,
        ProductOptionValueDetailComponent,
        ProductOptionValueDialogComponent,
        ProductOptionValueDeleteDialogComponent,
        ProductOptionValuePopupComponent,
        ProductOptionValueDeletePopupComponent,
    ],
    entryComponents: [
        ProductOptionValueComponent,
        ProductOptionValueDialogComponent,
        ProductOptionValuePopupComponent,
        ProductOptionValueDeleteDialogComponent,
        ProductOptionValueDeletePopupComponent,
    ],
    providers: [
        ProductOptionValueService,
        ProductOptionValuePopupService,
        ProductOptionValueResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductOptionValueModule {}
