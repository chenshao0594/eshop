import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionValueDescription } from './customer-option-value-description.model';
import { CustomerOptionValueDescriptionPopupService } from './customer-option-value-description-popup.service';
import { CustomerOptionValueDescriptionService } from './customer-option-value-description.service';
import { CustomerOptionValue, CustomerOptionValueService } from '../customer-option-value';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-customer-option-value-description-dialog',
    templateUrl: './customer-option-value-description-dialog.component.html'
})
export class CustomerOptionValueDescriptionDialogComponent implements OnInit {

    customerOptionValueDescription: CustomerOptionValueDescription;
    authorities: any[];
    isSaving: boolean;

    customeroptionvalues: CustomerOptionValue[];

    languages: Language[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerOptionValueDescriptionService: CustomerOptionValueDescriptionService,
        private customerOptionValueService: CustomerOptionValueService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionValueDescription']);
        this.customerOptionValueDescription = new CustomerOptionValueDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerOptionValueService.query().subscribe(
            (res: Response) => { this.customeroptionvalues = res.json(); }, (res: Response) => this.onError(res.json()));
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerOptionValueDescription.id !== undefined) {
            this.customerOptionValueDescriptionService.update(this.customerOptionValueDescription)
                .subscribe((res: CustomerOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionValueDescriptionService.create(this.customerOptionValueDescription)
                .subscribe((res: CustomerOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionValueDescription) {
        this.eventManager.broadcast({ name: 'customerOptionValueDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.customerOptionValueDescription = result;
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

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-option-value-description-popup',
    template: ''
})
export class CustomerOptionValueDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionValueDescriptionPopupService: CustomerOptionValueDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerOptionValueDescriptionPopupService
                    .open(CustomerOptionValueDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerOptionValueDescriptionPopupService
                    .open(CustomerOptionValueDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
