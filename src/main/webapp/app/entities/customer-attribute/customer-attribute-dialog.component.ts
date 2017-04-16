import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerAttribute } from './customer-attribute.model';
import { CustomerAttributePopupService } from './customer-attribute-popup.service';
import { CustomerAttributeService } from './customer-attribute.service';
import { CustomerOptionValue, CustomerOptionValueService } from '../customer-option-value';
import { CustomerOption, CustomerOptionService } from '../customer-option';
import { Customer, CustomerService } from '../customer';

@Component({
    selector: 'jhi-customer-attribute-dialog',
    templateUrl: './customer-attribute-dialog.component.html'
})
export class CustomerAttributeDialogComponent implements OnInit {

    customerAttribute: CustomerAttribute;
    authorities: any[];
    isSaving: boolean;

    customeroptionvalues: CustomerOptionValue[];

    customeroptions: CustomerOption[];

    customers: Customer[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerAttributeService: CustomerAttributeService,
        private customerOptionValueService: CustomerOptionValueService,
        private customerOptionService: CustomerOptionService,
        private customerService: CustomerService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerAttribute']);
        this.customerAttribute = new CustomerAttribute();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerOptionValueService.query().subscribe(
            (res: Response) => { this.customeroptionvalues = res.json(); }, (res: Response) => this.onError(res.json()));
        this.customerOptionService.query().subscribe(
            (res: Response) => { this.customeroptions = res.json(); }, (res: Response) => this.onError(res.json()));
        this.customerService.query().subscribe(
            (res: Response) => { this.customers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerAttribute.id !== undefined) {
            this.customerAttributeService.update(this.customerAttribute)
                .subscribe((res: CustomerAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerAttributeService.create(this.customerAttribute)
                .subscribe((res: CustomerAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerAttribute) {
        this.eventManager.broadcast({ name: 'customerAttributeListModification', content: 'OK'});
        this.isSaving = false;
        this.customerAttribute = result;
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

    trackCustomerOptionValueById(index: number, item: CustomerOptionValue) {
        return item.id;
    }

    trackCustomerOptionById(index: number, item: CustomerOption) {
        return item.id;
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-attribute-popup',
    template: ''
})
export class CustomerAttributePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerAttributePopupService: CustomerAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerAttributePopupService
                    .open(CustomerAttributeDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerAttributePopupService
                    .open(CustomerAttributeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
