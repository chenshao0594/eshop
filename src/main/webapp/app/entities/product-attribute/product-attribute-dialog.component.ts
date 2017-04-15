import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductAttribute } from './product-attribute.model';
import { ProductAttributePopupService } from './product-attribute-popup.service';
import { ProductAttributeService } from './product-attribute.service';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-product-attribute-dialog',
    templateUrl: './product-attribute-dialog.component.html'
})
export class ProductAttributeDialogComponent implements OnInit {

    productAttribute: ProductAttribute;
    authorities: any[];
    isSaving: boolean;

    products: Product[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productAttributeService: ProductAttributeService,
        private productService: ProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productAttribute']);
        this.productAttribute = new ProductAttribute();
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
        if (this.productAttribute.id !== undefined) {
            this.productAttributeService.update(this.productAttribute)
                .subscribe((res: ProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productAttributeService.create(this.productAttribute)
                .subscribe((res: ProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductAttribute) {
        this.eventManager.broadcast({ name: 'productAttributeListModification', content: 'OK'});
        this.isSaving = false;
        this.productAttribute = result;
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
    selector: 'jhi-product-attribute-popup',
    template: ''
})
export class ProductAttributePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productAttributePopupService: ProductAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productAttributePopupService
                    .open(ProductAttributeDialogComponent, params['id']);
            } else {
                this.modalRef = this.productAttributePopupService
                    .open(ProductAttributeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
