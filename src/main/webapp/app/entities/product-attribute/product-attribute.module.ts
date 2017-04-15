import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductAttributeService,
    ProductAttributePopupService,
    ProductAttributeComponent,
    ProductAttributeDetailComponent,
    ProductAttributeDialogComponent,
    ProductAttributePopupComponent,
    ProductAttributeDeletePopupComponent,
    ProductAttributeDeleteDialogComponent,
    productAttributeRoute,
    productAttributePopupRoute,
    ProductAttributeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productAttributeRoute,
    ...productAttributePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductAttributeComponent,
        ProductAttributeDetailComponent,
        ProductAttributeDialogComponent,
        ProductAttributeDeleteDialogComponent,
        ProductAttributePopupComponent,
        ProductAttributeDeletePopupComponent,
    ],
    entryComponents: [
        ProductAttributeComponent,
        ProductAttributeDialogComponent,
        ProductAttributePopupComponent,
        ProductAttributeDeleteDialogComponent,
        ProductAttributeDeletePopupComponent,
    ],
    providers: [
        ProductAttributeService,
        ProductAttributePopupService,
        ProductAttributeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductAttributeModule {}
