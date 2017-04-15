import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    DigitalProductService,
    DigitalProductPopupService,
    DigitalProductComponent,
    DigitalProductDetailComponent,
    DigitalProductDialogComponent,
    DigitalProductPopupComponent,
    DigitalProductDeletePopupComponent,
    DigitalProductDeleteDialogComponent,
    digitalProductRoute,
    digitalProductPopupRoute,
    DigitalProductResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...digitalProductRoute,
    ...digitalProductPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DigitalProductComponent,
        DigitalProductDetailComponent,
        DigitalProductDialogComponent,
        DigitalProductDeleteDialogComponent,
        DigitalProductPopupComponent,
        DigitalProductDeletePopupComponent,
    ],
    entryComponents: [
        DigitalProductComponent,
        DigitalProductDialogComponent,
        DigitalProductPopupComponent,
        DigitalProductDeleteDialogComponent,
        DigitalProductDeletePopupComponent,
    ],
    providers: [
        DigitalProductService,
        DigitalProductPopupService,
        DigitalProductResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopDigitalProductModule {}
