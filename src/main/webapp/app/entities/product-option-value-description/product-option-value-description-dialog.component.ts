import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ProductOptionValueDescription } from './product-option-value-description.model';
import { ProductOptionValueDescriptionPopupService } from './product-option-value-description-popup.service';
import { ProductOptionValueDescriptionService } from './product-option-value-description.service';
import { ProductOptionValue, ProductOptionValueService } from '../product-option-value';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-product-option-value-description-dialog',
    templateUrl: './product-option-value-description-dialog.component.html'
})
export class ProductOptionValueDescriptionDialogComponent implements OnInit {

    productOptionValueDescription: ProductOptionValueDescription;
    authorities: any[];
    isSaving: boolean;

    productoptionvalues: ProductOptionValue[];

    languages: Language[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productOptionValueDescriptionService: ProductOptionValueDescriptionService,
        private productOptionValueService: ProductOptionValueService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['productOptionValueDescription']);
        this.productOptionValueDescription = new ProductOptionValueDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productOptionValueService.query().subscribe(
            (res: Response) => { this.productoptionvalues = res.json(); }, (res: Response) => this.onError(res.json()));
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productOptionValueDescription.id !== undefined) {
            this.productOptionValueDescriptionService.update(this.productOptionValueDescription)
                .subscribe((res: ProductOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionValueDescriptionService.create(this.productOptionValueDescription)
                .subscribe((res: ProductOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOptionValueDescription) {
        this.eventManager.broadcast({ name: 'productOptionValueDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.productOptionValueDescription = result;
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

    trackProductOptionValueById(index: number, item: ProductOptionValue) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-product-option-value-description-popup',
    template: ''
})
export class ProductOptionValueDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productOptionValueDescriptionPopupService: ProductOptionValueDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productOptionValueDescriptionPopupService
                    .open(ProductOptionValueDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.productOptionValueDescriptionPopupService
                    .open(ProductOptionValueDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
