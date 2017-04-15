import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductImage } from './product-image.model';
import { ProductImagePopupService } from './product-image-popup.service';
import { ProductImageService } from './product-image.service';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-product-image-dialog',
    templateUrl: './product-image-dialog.component.html'
})
export class ProductImageDialogComponent implements OnInit {

    productImage: ProductImage;
    authorities: any[];
    isSaving: boolean;

    products: Product[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productImageService: ProductImageService,
        private productService: ProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productImage']);
        this.productImage = new ProductImage();
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
        if (this.productImage.id !== undefined) {
            this.productImageService.update(this.productImage)
                .subscribe((res: ProductImage) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productImageService.create(this.productImage)
                .subscribe((res: ProductImage) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductImage) {
        this.eventManager.broadcast({ name: 'productImageListModification', content: 'OK'});
        this.isSaving = false;
        this.productImage = result;
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
    selector: 'jhi-product-image-popup',
    template: ''
})
export class ProductImagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productImagePopupService: ProductImagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productImagePopupService
                    .open(ProductImageDialogComponent, params['id']);
            } else {
                this.modalRef = this.productImagePopupService
                    .open(ProductImageDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
