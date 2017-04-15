import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    OptinService,
    OptinPopupService,
    OptinComponent,
    OptinDetailComponent,
    OptinDialogComponent,
    OptinPopupComponent,
    OptinDeletePopupComponent,
    OptinDeleteDialogComponent,
    optinRoute,
    optinPopupRoute,
    OptinResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...optinRoute,
    ...optinPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OptinComponent,
        OptinDetailComponent,
        OptinDialogComponent,
        OptinDeleteDialogComponent,
        OptinPopupComponent,
        OptinDeletePopupComponent,
    ],
    entryComponents: [
        OptinComponent,
        OptinDialogComponent,
        OptinPopupComponent,
        OptinDeleteDialogComponent,
        OptinDeletePopupComponent,
    ],
    providers: [
        OptinService,
        OptinPopupService,
        OptinResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopOptinModule {}
