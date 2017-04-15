import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    FileHistoryService,
    FileHistoryPopupService,
    FileHistoryComponent,
    FileHistoryDetailComponent,
    FileHistoryDialogComponent,
    FileHistoryPopupComponent,
    FileHistoryDeletePopupComponent,
    FileHistoryDeleteDialogComponent,
    fileHistoryRoute,
    fileHistoryPopupRoute,
    FileHistoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fileHistoryRoute,
    ...fileHistoryPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FileHistoryComponent,
        FileHistoryDetailComponent,
        FileHistoryDialogComponent,
        FileHistoryDeleteDialogComponent,
        FileHistoryPopupComponent,
        FileHistoryDeletePopupComponent,
    ],
    entryComponents: [
        FileHistoryComponent,
        FileHistoryDialogComponent,
        FileHistoryPopupComponent,
        FileHistoryDeleteDialogComponent,
        FileHistoryDeletePopupComponent,
    ],
    providers: [
        FileHistoryService,
        FileHistoryPopupService,
        FileHistoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopFileHistoryModule {}
