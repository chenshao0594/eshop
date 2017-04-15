import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductType } from './product-type.model';
import { ProductTypePopupService } from './product-type-popup.service';
import { ProductTypeService } from './product-type.service';

@Component({
    selector: 'jhi-product-type-dialog',
    templateUrl: './product-type-dialog.component.html'
})
export class ProductTypeDialogComponent implements OnInit {

    productType: ProductType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productTypeService: ProductTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productType']);
        this.productType = new ProductType();
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
        if (this.productType.id !== undefined) {
            this.productTypeService.update(this.productType)
                .subscribe((res: ProductType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productTypeService.create(this.productType)
                .subscribe((res: ProductType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductType) {
        this.eventManager.broadcast({ name: 'productTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.productType = result;
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
    selector: 'jhi-product-type-popup',
    template: ''
})
export class ProductTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productTypePopupService: ProductTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productTypePopupService
                    .open(ProductTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.productTypePopupService
                    .open(ProductTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
