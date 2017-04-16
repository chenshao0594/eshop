import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import {
    EmailSettingService,
    EmailSettingPopupService,
    EmailSettingComponent,
    EmailSettingDetailComponent,
    EmailSettingDialogComponent,
    EmailSettingPopupComponent,
    EmailSettingDeletePopupComponent,
    EmailSettingDeleteDialogComponent,
    emailSettingRoute,
    emailSettingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...emailSettingRoute,
    ...emailSettingPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmailSettingComponent,
        EmailSettingDetailComponent,
        EmailSettingDialogComponent,
        EmailSettingDeleteDialogComponent,
        EmailSettingPopupComponent,
        EmailSettingDeletePopupComponent,
    ],
    entryComponents: [
        EmailSettingComponent,
        EmailSettingDialogComponent,
        EmailSettingPopupComponent,
        EmailSettingDeleteDialogComponent,
        EmailSettingDeletePopupComponent,
    ],
    providers: [
        EmailSettingService,
        EmailSettingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopEmailSettingModule {}
