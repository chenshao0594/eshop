import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionSet } from './customer-option-set.model';
import { CustomerOptionSetPopupService } from './customer-option-set-popup.service';
import { CustomerOptionSetService } from './customer-option-set.service';
import { CustomerOption, CustomerOptionService } from '../customer-option';
import { CustomerOptionValue, CustomerOptionValueService } from '../customer-option-value';

@Component({
    selector: 'jhi-customer-option-set-dialog',
    templateUrl: './customer-option-set-dialog.component.html'
})
export class CustomerOptionSetDialogComponent implements OnInit {

    customerOptionSet: CustomerOptionSet;
    authorities: any[];
    isSaving: boolean;

    customeroptions: CustomerOption[];

    customeroptionvalues: CustomerOptionValue[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerOptionSetService: CustomerOptionSetService,
        private customerOptionService: CustomerOptionService,
        private customerOptionValueService: CustomerOptionValueService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionSet']);
        this.customerOptionSet = new CustomerOptionSet();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerOptionService.query().subscribe(
            (res: Response) => { this.customeroptions = res.json(); }, (res: Response) => this.onError(res.json()));
        this.customerOptionValueService.query().subscribe(
            (res: Response) => { this.customeroptionvalues = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerOptionSet.id !== undefined) {
            this.customerOptionSetService.update(this.customerOptionSet)
                .subscribe((res: CustomerOptionSet) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionSetService.create(this.customerOptionSet)
                .subscribe((res: CustomerOptionSet) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionSet) {
        this.eventManager.broadcast({ name: 'customerOptionSetListModification', content: 'OK'});
        this.isSaving = false;
        this.customerOptionSet = result;
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

    trackCustomerOptionById(index: number, item: CustomerOption) {
        return item.id;
    }

    trackCustomerOptionValueById(index: number, item: CustomerOptionValue) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-option-set-popup',
    template: ''
})
export class CustomerOptionSetPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionSetPopupService: CustomerOptionSetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerOptionSetPopupService
                    .open(CustomerOptionSetDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerOptionSetPopupService
                    .open(CustomerOptionSetDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
