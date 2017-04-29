import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductOptionService,
    ProductOptionPopupService,
    ProductOptionComponent,
    ProductOptionDetailComponent,
    ProductOptionDialogComponent,
    ProductOptionPopupComponent,
    ProductOptionDeletePopupComponent,
    ProductOptionDeleteDialogComponent,
    productOptionRoute,
    productOptionPopupRoute,
    ProductOptionResolvePagingParams
} from './';

const ENTITY_STATES = [
    ...productOptionRoute,
    ...productOptionPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductOptionComponent,
        ProductOptionDetailComponent,
        ProductOptionDialogComponent,
        ProductOptionDeleteDialogComponent,
        ProductOptionPopupComponent,
        ProductOptionDeletePopupComponent
    ],
    entryComponents: [
        ProductOptionComponent,
        ProductOptionDialogComponent,
        ProductOptionPopupComponent,
        ProductOptionDeleteDialogComponent,
        ProductOptionDeletePopupComponent
    ],
    providers: [
        ProductOptionService,
        ProductOptionPopupService,
        ProductOptionResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductOptionModule {}
