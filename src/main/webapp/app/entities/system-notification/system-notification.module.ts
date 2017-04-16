import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EshopSharedModule } from '../../shared';
import { EshopAdminModule } from '../../admin/admin.module';
import {
    SystemNotificationService,
    SystemNotificationPopupService,
    SystemNotificationComponent,
    SystemNotificationDetailComponent,
    SystemNotificationDialogComponent,
    SystemNotificationPopupComponent,
    SystemNotificationDeletePopupComponent,
    SystemNotificationDeleteDialogComponent,
    systemNotificationRoute,
    systemNotificationPopupRoute,
    SystemNotificationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...systemNotificationRoute,
    ...systemNotificationPopupRoute,
];

@NgModule({
    imports: [
        EshopSharedModule,
        EshopAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SystemNotificationComponent,
        SystemNotificationDetailComponent,
        SystemNotificationDialogComponent,
        SystemNotificationDeleteDialogComponent,
        SystemNotificationPopupComponent,
        SystemNotificationDeletePopupComponent,
    ],
    entryComponents: [
        SystemNotificationComponent,
        SystemNotificationDialogComponent,
        SystemNotificationPopupComponent,
        SystemNotificationDeleteDialogComponent,
        SystemNotificationDeletePopupComponent,
    ],
    providers: [
        SystemNotificationService,
        SystemNotificationPopupService,
        SystemNotificationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EshopSystemNotificationModule {}
