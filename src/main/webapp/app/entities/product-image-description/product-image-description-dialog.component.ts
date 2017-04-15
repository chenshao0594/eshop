import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductImageDescription } from './product-image-description.model';
import { ProductImageDescriptionPopupService } from './product-image-description-popup.service';
import { ProductImageDescriptionService } from './product-image-description.service';
import { ProductImage, ProductImageService } from '../product-image';

@Component({
    selector: 'jhi-product-image-description-dialog',
    templateUrl: './product-image-description-dialog.component.html'
})
export class ProductImageDescriptionDialogComponent implements OnInit {

    productImageDescription: ProductImageDescription;
    authorities: any[];
    isSaving: boolean;

    productimages: ProductImage[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productImageDescriptionService: ProductImageDescriptionService,
        private productImageService: ProductImageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productImageDescription']);
        this.productImageDescription = new ProductImageDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productImageService.query().subscribe(
            (res: Response) => { this.productimages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productImageDescription.id !== undefined) {
            this.productImageDescriptionService.update(this.productImageDescription)
                .subscribe((res: ProductImageDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productImageDescriptionService.create(this.productImageDescription)
                .subscribe((res: ProductImageDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductImageDescription) {
        this.eventManager.broadcast({ name: 'productImageDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.productImageDescription = result;
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

    trackProductImageById(index: number, item: ProductImage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-image-description-popup',
    template: ''
})
export class ProductImageDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productImageDescriptionPopupService: ProductImageDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productImageDescriptionPopupService
                    .open(ProductImageDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.productImageDescriptionPopupService
                    .open(ProductImageDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
