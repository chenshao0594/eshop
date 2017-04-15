import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerOptionDescription } from './customer-option-description.model';
import { CustomerOptionDescriptionPopupService } from './customer-option-description-popup.service';
import { CustomerOptionDescriptionService } from './customer-option-description.service';
import { CustomerOption, CustomerOptionService } from '../customer-option';

@Component({
    selector: 'jhi-customer-option-description-dialog',
    templateUrl: './customer-option-description-dialog.component.html'
})
export class CustomerOptionDescriptionDialogComponent implements OnInit {

    customerOptionDescription: CustomerOptionDescription;
    authorities: any[];
    isSaving: boolean;

    customeroptions: CustomerOption[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerOptionDescriptionService: CustomerOptionDescriptionService,
        private customerOptionService: CustomerOptionService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOptionDescription']);
        this.customerOptionDescription = new CustomerOptionDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.customerOptionService.query().subscribe(
            (res: Response) => { this.customeroptions = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerOptionDescription.id !== undefined) {
            this.customerOptionDescriptionService.update(this.customerOptionDescription)
                .subscribe((res: CustomerOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionDescriptionService.create(this.customerOptionDescription)
                .subscribe((res: CustomerOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionDescription) {
        this.eventManager.broadcast({ name: 'customerOptionDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.customerOptionDescription = result;
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
}

@Component({
    selector: 'jhi-customer-option-description-popup',
    template: ''
})
export class CustomerOptionDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionDescriptionPopupService: CustomerOptionDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerOptionDescriptionPopupService
                    .open(CustomerOptionDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerOptionDescriptionPopupService
                    .open(CustomerOptionDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
