import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductOptionDescription } from './product-option-description.model';
import { ProductOptionDescriptionPopupService } from './product-option-description-popup.service';
import { ProductOptionDescriptionService } from './product-option-description.service';
import { ProductOption, ProductOptionService } from '../product-option';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-product-option-description-dialog',
    templateUrl: './product-option-description-dialog.component.html'
})
export class ProductOptionDescriptionDialogComponent implements OnInit {

    productOptionDescription: ProductOptionDescription;
    authorities: any[];
    isSaving: boolean;

    productoptions: ProductOption[];

    languages: Language[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productOptionDescriptionService: ProductOptionDescriptionService,
        private productOptionService: ProductOptionService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productOptionDescription']);
        this.productOptionDescription = new ProductOptionDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productOptionService.query().subscribe(
            (res: Response) => { this.productoptions = res.json(); }, (res: Response) => this.onError(res.json()));
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productOptionDescription.id !== undefined) {
            this.productOptionDescriptionService.update(this.productOptionDescription)
                .subscribe((res: ProductOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionDescriptionService.create(this.productOptionDescription)
                .subscribe((res: ProductOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOptionDescription) {
        this.eventManager.broadcast({ name: 'productOptionDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.productOptionDescription = result;
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

    trackProductOptionById(index: number, item: ProductOption) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-option-description-popup',
    template: ''
})
export class ProductOptionDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionDescriptionPopupService: ProductOptionDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productOptionDescriptionPopupService
                    .open(ProductOptionDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.productOptionDescriptionPopupService
                    .open(ProductOptionDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
