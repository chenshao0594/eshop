import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MerchantLog } from './merchant-log.model';
import { MerchantLogPopupService } from './merchant-log-popup.service';
import { MerchantLogService } from './merchant-log.service';

@Component({
    selector: 'jhi-merchant-log-dialog',
    templateUrl: './merchant-log-dialog.component.html'
})
export class MerchantLogDialogComponent implements OnInit {

    merchantLog: MerchantLog;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private merchantLogService: MerchantLogService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['merchantLog']);
        this.merchantLog = new MerchantLog();
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
        if (this.merchantLog.id !== undefined) {
            this.merchantLogService.update(this.merchantLog)
                .subscribe((res: MerchantLog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.merchantLogService.create(this.merchantLog)
                .subscribe((res: MerchantLog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MerchantLog) {
        this.eventManager.broadcast({ name: 'merchantLogListModification', content: 'OK'});
        this.isSaving = false;
        this.merchantLog = result;
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
    selector: 'jhi-merchant-log-popup',
    template: ''
})
export class MerchantLogPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantLogPopupService: MerchantLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.merchantLogPopupService
                    .open(MerchantLogDialogComponent, params['id']);
            } else {
                this.modalRef = this.merchantLogPopupService
                    .open(MerchantLogDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
