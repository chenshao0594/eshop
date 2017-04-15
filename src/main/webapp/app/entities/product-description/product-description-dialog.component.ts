import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductDescription } from './product-description.model';
import { ProductDescriptionPopupService } from './product-description-popup.service';
import { ProductDescriptionService } from './product-description.service';
import { Product, ProductService } from '../product';

@Component({
    selector: 'jhi-product-description-dialog',
    templateUrl: './product-description-dialog.component.html'
})
export class ProductDescriptionDialogComponent implements OnInit {

    productDescription: ProductDescription;
    authorities: any[];
    isSaving: boolean;

    products: Product[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productDescriptionService: ProductDescriptionService,
        private productService: ProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productDescription']);
        this.productDescription = new ProductDescription();
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
        if (this.productDescription.id !== undefined) {
            this.productDescriptionService.update(this.productDescription)
                .subscribe((res: ProductDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productDescriptionService.create(this.productDescription)
                .subscribe((res: ProductDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductDescription) {
        this.eventManager.broadcast({ name: 'productDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.productDescription = result;
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
    selector: 'jhi-product-description-popup',
    template: ''
})
export class ProductDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productDescriptionPopupService: ProductDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productDescriptionPopupService
                    .open(ProductDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.productDescriptionPopupService
                    .open(ProductDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
