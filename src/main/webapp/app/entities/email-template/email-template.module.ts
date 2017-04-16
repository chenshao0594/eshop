import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    EmailTemplateService,
    EmailTemplatePopupService,
    EmailTemplateComponent,
    EmailTemplateDetailComponent,
    EmailTemplateDialogComponent,
    EmailTemplatePopupComponent,
    EmailTemplateDeletePopupComponent,
    EmailTemplateDeleteDialogComponent,
    emailTemplateRoute,
    emailTemplatePopupRoute,
    EmailTemplateResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...emailTemplateRoute,
    ...emailTemplatePopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmailTemplateComponent,
        EmailTemplateDetailComponent,
        EmailTemplateDialogComponent,
        EmailTemplateDeleteDialogComponent,
        EmailTemplatePopupComponent,
        EmailTemplateDeletePopupComponent,
    ],
    entryComponents: [
        EmailTemplateComponent,
        EmailTemplateDialogComponent,
        EmailTemplatePopupComponent,
        EmailTemplateDeleteDialogComponent,
        EmailTemplateDeletePopupComponent,
    ],
    providers: [
        EmailTemplateService,
        EmailTemplatePopupService,
        EmailTemplateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopEmailTemplateModule {}
