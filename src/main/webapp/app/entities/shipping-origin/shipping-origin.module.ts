import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ShippingOriginService,
    ShippingOriginPopupService,
    ShippingOriginComponent,
    ShippingOriginDetailComponent,
    ShippingOriginDialogComponent,
    ShippingOriginPopupComponent,
    ShippingOriginDeletePopupComponent,
    ShippingOriginDeleteDialogComponent,
    shippingOriginRoute,
    shippingOriginPopupRoute,
    ShippingOriginResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shippingOriginRoute,
    ...shippingOriginPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShippingOriginComponent,
        ShippingOriginDetailComponent,
        ShippingOriginDialogComponent,
        ShippingOriginDeleteDialogComponent,
        ShippingOriginPopupComponent,
        ShippingOriginDeletePopupComponent,
    ],
    entryComponents: [
        ShippingOriginComponent,
        ShippingOriginDialogComponent,
        ShippingOriginPopupComponent,
        ShippingOriginDeleteDialogComponent,
        ShippingOriginDeletePopupComponent,
    ],
    providers: [
        ShippingOriginService,
        ShippingOriginPopupService,
        ShippingOriginResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopShippingOriginModule {}
