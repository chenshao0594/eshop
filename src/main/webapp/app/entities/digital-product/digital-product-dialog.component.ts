import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { DigitalProduct } from './digital-product.model';
import { DigitalProductPopupService } from './digital-product-popup.service';
import { DigitalProductService } from './digital-product.service';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-digital-product-dialog',
    templateUrl: './digital-product-dialog.component.html'
})
export class DigitalProductDialogComponent implements OnInit {

    digitalProduct: DigitalProduct;
    authorities: any[];
    isSaving: boolean;

    products: Product[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private digitalProductService: DigitalProductService,
        private productService: ProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['digitalProduct']);
        this.digitalProduct = new DigitalProduct();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => { this.products = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.digitalProduct.id !== undefined) {
            this.digitalProductService.update(this.digitalProduct)
                .subscribe((res: DigitalProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.digitalProductService.create(this.digitalProduct)
                .subscribe((res: DigitalProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: DigitalProduct) {
        this.eventManager.broadcast({ name: 'digitalProductListModification', content: 'OK'});
        this.isSaving = false;
        this.digitalProduct = result;
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

    trackProductById(index: number, item: Product) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-digital-product-popup',
    template: ''
})
export class DigitalProductPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private digitalProductPopupService: DigitalProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.digitalProductPopupService
                    .open(DigitalProductDialogComponent, params['id']);
            } else {
                this.modalRef = this.digitalProductPopupService
                    .open(DigitalProductDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
