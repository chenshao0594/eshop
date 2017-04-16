import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { EmailSetting } from './email-setting.model';
import { EmailSettingPopupService } from './email-setting-popup.service';
import { EmailSettingService } from './email-setting.service';

@Component({
    selector: 'jhi-email-setting-dialog',
    templateUrl: './email-setting-dialog.component.html'
})
export class EmailSettingDialogComponent implements OnInit {

    emailSetting: EmailSetting;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private emailSettingService: EmailSettingService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['emailSetting', 'sMTPSecurityEnum']);
        this.emailSetting = new EmailSetting();
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
        if (this.emailSetting.id !== undefined) {
            this.emailSettingService.update(this.emailSetting)
                .subscribe((res: EmailSetting) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.emailSettingService.create(this.emailSetting)
                .subscribe((res: EmailSetting) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: EmailSetting) {
        this.eventManager.broadcast({ name: 'emailSettingListModification', content: 'OK'});
        this.isSaving = false;
        this.emailSetting = result;
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
    selector: 'jhi-email-setting-popup',
    template: ''
})
export class EmailSettingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailSettingPopupService: EmailSettingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.emailSettingPopupService
                    .open(EmailSettingDialogComponent, params['id']);
            } else {
                this.modalRef = this.emailSettingPopupService
                    .open(EmailSettingDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
