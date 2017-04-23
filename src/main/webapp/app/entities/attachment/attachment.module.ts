import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    AttachmentService,
    AttachmentPopupService,
    AttachmentComponent,
    AttachmentDetailComponent,
    AttachmentDialogComponent,
    AttachmentPopupComponent,
    AttachmentDeletePopupComponent,
    AttachmentDeleteDialogComponent,
    attachmentRoute,
    attachmentPopupRoute,
    AttachmentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...attachmentRoute,
    ...attachmentPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AttachmentComponent,
        AttachmentDetailComponent,
        AttachmentDialogComponent,
        AttachmentDeleteDialogComponent,
        AttachmentPopupComponent,
        AttachmentDeletePopupComponent,
    ],
    entryComponents: [
        AttachmentComponent,
        AttachmentDialogComponent,
        AttachmentPopupComponent,
        AttachmentDeleteDialogComponent,
        AttachmentDeletePopupComponent,
    ],
    providers: [
        AttachmentService,
        AttachmentPopupService,
        AttachmentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopAttachmentModule {}
