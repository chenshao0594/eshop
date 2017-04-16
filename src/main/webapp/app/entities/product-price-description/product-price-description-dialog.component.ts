import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductPriceDescription } from './product-price-description.model';
import { ProductPriceDescriptionPopupService } from './product-price-description-popup.service';
import { ProductPriceDescriptionService } from './product-price-description.service';
import { ProductPrice, ProductPriceService } from '../product-price';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-product-price-description-dialog',
    templateUrl: './product-price-description-dialog.component.html'
})
export class ProductPriceDescriptionDialogComponent implements OnInit {

    productPriceDescription: ProductPriceDescription;
    authorities: any[];
    isSaving: boolean;

    productprices: ProductPrice[];

    languages: Language[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productPriceDescriptionService: ProductPriceDescriptionService,
        private productPriceService: ProductPriceService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productPriceDescription']);
        this.productPriceDescription = new ProductPriceDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productPriceService.query().subscribe(
            (res: Response) => { this.productprices = res.json(); }, (res: Response) => this.onError(res.json()));
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productPriceDescription.id !== undefined) {
            this.productPriceDescriptionService.update(this.productPriceDescription)
                .subscribe((res: ProductPriceDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productPriceDescriptionService.create(this.productPriceDescription)
                .subscribe((res: ProductPriceDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductPriceDescription) {
        this.eventManager.broadcast({ name: 'productPriceDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.productPriceDescription = result;
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

    trackProductPriceById(index: number, item: ProductPrice) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-price-description-popup',
    template: ''
})
export class ProductPriceDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productPriceDescriptionPopupService: ProductPriceDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productPriceDescriptionPopupService
                    .open(ProductPriceDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.productPriceDescriptionPopupService
                    .open(ProductPriceDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
