import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerOption } from './customer-option.model';
import { CustomerOptionPopupService } from './customer-option-popup.service';
import { CustomerOptionService } from './customer-option.service';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-customer-option-dialog',
    templateUrl: './customer-option-dialog.component.html'
})
export class CustomerOptionDialogComponent implements OnInit {

    customerOption: CustomerOption;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerOptionService: CustomerOptionService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerOption']);
        this.customerOption = new CustomerOption();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.customerOption.id !== undefined) {
            this.customerOptionService.update(this.customerOption)
                .subscribe((res: CustomerOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionService.create(this.customerOption)
                .subscribe((res: CustomerOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOption) {
        this.eventManager.broadcast({ name: 'customerOptionListModification', content: 'OK'});
        this.isSaving = false;
        this.customerOption = result;
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
    selector: 'jhi-customer-option-popup',
    template: ''
})
export class CustomerOptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerOptionPopupService: CustomerOptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.customerOptionPopupService
                    .open(CustomerOptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerOptionPopupService
                    .open(CustomerOptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
