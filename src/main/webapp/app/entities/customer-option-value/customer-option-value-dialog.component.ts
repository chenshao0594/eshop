import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionValue } from './customer-option-value.model';
import { CustomerOptionValuePopupService } from './customer-option-value-popup.service';
import { CustomerOptionValueService } from './customer-option-value.service';

@Component({
    selector: 'jhi-customer-option-value-dialog',
    templateUrl: './customer-option-value-dialog.component.html'
})
export class CustomerOptionValueDialogComponent implements OnInit {

    customerOptionValue: CustomerOptionValue;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerOptionValueService: CustomerOptionValueService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionValue']);
        this.customerOptionValue = new CustomerOptionValue();
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
        if (this.customerOptionValue.id !== undefined) {
            this.customerOptionValueService.update(this.customerOptionValue)
                .subscribe((res: CustomerOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionValueService.create(this.customerOptionValue)
                .subscribe((res: CustomerOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionValue) {
        this.eventManager.broadcast({ name: 'customerOptionValueListModification', content: 'OK'});
        this.isSaving = false;
        this.customerOptionValue = result;
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
    selector: 'jhi-customer-option-value-popup',
    template: ''
})
export class CustomerOptionValuePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionValuePopupService: CustomerOptionValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerOptionValuePopupService
                    .open(CustomerOptionValueDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerOptionValuePopupService
                    .open(CustomerOptionValueDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
