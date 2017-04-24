import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MerchantLog } from './merchant-log.model';
import { MerchantLogPopupService } from './merchant-log-popup.service';
import { MerchantLogService } from './merchant-log.service';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-merchant-log-dialog',
    templateUrl: './merchant-log-dialog.component.html'
})
export class MerchantLogDialogComponent implements OnInit {

    merchantLog: MerchantLog;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private merchantLogService: MerchantLogService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['merchantLog']);
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.load(params['id']);
            } else {
                this.merchantLog = new MerchantLog();
            }
        });
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    load(id) {
         this.merchantLogService.find(id).subscribe((merchantLog) => {
                this.merchantLog = merchantLog;
                
         });
    }
    clear() {
       // this.activeModal.dismiss('cancel');
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
      //  this.activeModal.dismiss(result);
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

    trackMerchantStoreById(index: number, item: MerchantStore) {
        return item.id;
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
