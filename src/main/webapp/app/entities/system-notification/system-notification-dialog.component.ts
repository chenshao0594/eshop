import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SystemNotification } from './system-notification.model';
import { SystemNotificationPopupService } from './system-notification-popup.service';
import { SystemNotificationService } from './system-notification.service';

@Component({
    selector: 'jhi-system-notification-dialog',
    templateUrl: './system-notification-dialog.component.html'
})
export class SystemNotificationDialogComponent implements OnInit {

    systemNotification: SystemNotification;
    authorities: any[];
    isSaving: boolean;
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private systemNotificationService: SystemNotificationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['systemNotification']);
        this.systemNotification = new SystemNotification();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.systemNotification.id !== undefined) {
            this.systemNotificationService.update(this.systemNotification)
                .subscribe((res: SystemNotification) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.systemNotificationService.create(this.systemNotification)
                .subscribe((res: SystemNotification) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: SystemNotification) {
        this.eventManager.broadcast({ name: 'systemNotificationListModification', content: 'OK'});
        this.isSaving = false;
        this.systemNotification = result;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-system-notification-popup',
    template: ''
})
export class SystemNotificationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private systemNotificationPopupService: SystemNotificationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.systemNotificationPopupService
                    .open(SystemNotificationDialogComponent, params['id']);
            } else {
                this.modalRef = this.systemNotificationPopupService
                    .open(SystemNotificationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
