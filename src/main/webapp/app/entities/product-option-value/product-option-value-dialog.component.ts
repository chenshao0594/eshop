import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductOptionValue } from './product-option-value.model';
import { ProductOptionValuePopupService } from './product-option-value-popup.service';
import { ProductOptionValueService } from './product-option-value.service';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-product-option-value-dialog',
    templateUrl: './product-option-value-dialog.component.html'
})
export class ProductOptionValueDialogComponent implements OnInit {

    productOptionValue: ProductOptionValue;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productOptionValueService: ProductOptionValueService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productOptionValue']);
        this.productOptionValue = new ProductOptionValue();
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
        if (this.productOptionValue.id !== undefined) {
            this.productOptionValueService.update(this.productOptionValue)
                .subscribe((res: ProductOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionValueService.create(this.productOptionValue)
                .subscribe((res: ProductOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOptionValue) {
        this.eventManager.broadcast({ name: 'productOptionValueListModification', content: 'OK'});
        this.isSaving = false;
        this.productOptionValue = result;
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
    selector: 'jhi-product-option-value-popup',
    template: ''
})
export class ProductOptionValuePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionValuePopupService: ProductOptionValuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productOptionValuePopupService
                    .open(ProductOptionValueDialogComponent, params['id']);
            } else {
                this.modalRef = this.productOptionValuePopupService
                    .open(ProductOptionValueDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
