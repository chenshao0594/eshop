import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductOption } from './product-option.model';
import { ProductOptionPopupService } from './product-option-popup.service';
import { ProductOptionService } from './product-option.service';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-product-option-dialog',
    templateUrl: './product-option-dialog.component.html'
})
export class ProductOptionDialogComponent implements OnInit {

    productOption: ProductOption;
    authorities: any[];
    isSaving: boolean;

    merchantstores: MerchantStore[];
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productOptionService: ProductOptionService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['productOption']);
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.load(params['id']);
            } else {
                this.productOption = new ProductOption();
            }
        });
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    load(id) {
         this.productOptionService.find(id).subscribe((productOption) => {
                this.productOption = productOption;
                
         });
    }
    clear() {
       // this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.productOption.id !== undefined) {
            this.productOptionService.update(this.productOption)
                .subscribe((res: ProductOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionService.create(this.productOption)
                .subscribe((res: ProductOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOption) {
        this.eventManager.broadcast({ name: 'productOptionListModification', content: 'OK'});
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
    selector: 'jhi-product-option-popup',
    template: ''
})
export class ProductOptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionPopupService: ProductOptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productOptionPopupService
                    .open(ProductOptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.productOptionPopupService
                    .open(ProductOptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
