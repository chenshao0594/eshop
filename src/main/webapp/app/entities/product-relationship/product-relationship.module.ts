import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    ProductRelationshipService,
    ProductRelationshipPopupService,
    ProductRelationshipComponent,
    ProductRelationshipDetailComponent,
    ProductRelationshipDialogComponent,
    ProductRelationshipPopupComponent,
    ProductRelationshipDeletePopupComponent,
    ProductRelationshipDeleteDialogComponent,
    productRelationshipRoute,
    productRelationshipPopupRoute,
    ProductRelationshipResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productRelationshipRoute,
    ...productRelationshipPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductRelationshipComponent,
        ProductRelationshipDetailComponent,
        ProductRelationshipDialogComponent,
        ProductRelationshipDeleteDialogComponent,
        ProductRelationshipPopupComponent,
        ProductRelationshipDeletePopupComponent,
    ],
    entryComponents: [
        ProductRelationshipComponent,
        ProductRelationshipDialogComponent,
        ProductRelationshipPopupComponent,
        ProductRelationshipDeleteDialogComponent,
        ProductRelationshipDeletePopupComponent,
    ],
    providers: [
        ProductRelationshipService,
        ProductRelationshipPopupService,
        ProductRelationshipResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopProductRelationshipModule {}
