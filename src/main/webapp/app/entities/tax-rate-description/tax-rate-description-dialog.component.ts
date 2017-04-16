import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { TaxRateDescription } from './tax-rate-description.model';
import { TaxRateDescriptionPopupService } from './tax-rate-description-popup.service';
import { TaxRateDescriptionService } from './tax-rate-description.service';
import { TaxRate, TaxRateService } from '../tax-rate';
import { Language, LanguageService } from '../language';

@Component({
    selector: 'jhi-tax-rate-description-dialog',
    templateUrl: './tax-rate-description-dialog.component.html'
})
export class TaxRateDescriptionDialogComponent implements OnInit {

    taxRateDescription: TaxRateDescription;
    authorities: any[];
    isSaving: boolean;

    taxrates: TaxRate[];

    languages: Language[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private taxRateDescriptionService: TaxRateDescriptionService,
        private taxRateService: TaxRateService,
        private languageService: LanguageService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taxRateDescription']);
        this.taxRateDescription = new TaxRateDescription();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.taxRateService.query().subscribe(
            (res: Response) => { this.taxrates = res.json(); }, (res: Response) => this.onError(res.json()));
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.taxRateDescription.id !== undefined) {
            this.taxRateDescriptionService.update(this.taxRateDescription)
                .subscribe((res: TaxRateDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.taxRateDescriptionService.create(this.taxRateDescription)
                .subscribe((res: TaxRateDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: TaxRateDescription) {
        this.eventManager.broadcast({ name: 'taxRateDescriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.taxRateDescription = result;
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

    trackTaxRateById(index: number, item: TaxRate) {
        return item.id;
    }

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tax-rate-description-popup',
    template: ''
})
export class TaxRateDescriptionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taxRateDescriptionPopupService: TaxRateDescriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.taxRateDescriptionPopupService
                    .open(TaxRateDescriptionDialogComponent, params['id']);
            } else {
                this.modalRef = this.taxRateDescriptionPopupService
                    .open(TaxRateDescriptionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
