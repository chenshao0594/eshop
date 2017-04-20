import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Billing } from './billing.model';
import { BillingPopupService } from './billing-popup.service';
import { BillingService } from './billing.service';

@Component({
    selector: 'jhi-billing-dialog',
    templateUrl: './billing-dialog.component.html'
})
export class BillingDialogComponent implements OnInit {

    billing: Billing;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private billingService: BillingService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['billing']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.billing.id !== undefined) {
            this.billingService.update(this.billing)
                .subscribe((res: Billing) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.billingService.create(this.billing)
                .subscribe((res: Billing) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Billing) {
        this.eventManager.broadcast({ name: 'billingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
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
    selector: 'jhi-billing-popup',
    template: ''
})
export class BillingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private billingPopupService: BillingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.billingPopupService
                    .open(BillingDialogComponent, params['id']);
            } else {
                this.modalRef = this.billingPopupService
                    .open(BillingDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
