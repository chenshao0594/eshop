import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { TaxRate } from './tax-rate.model';
import { TaxRatePopupService } from './tax-rate-popup.service';
import { TaxRateService } from './tax-rate.service';
import { TaxClass, TaxClassService } from '../tax-class';

@Component({
    selector: 'jhi-tax-rate-dialog',
    templateUrl: './tax-rate-dialog.component.html'
})
export class TaxRateDialogComponent implements OnInit {

    taxRate: TaxRate;
    authorities: any[];
    isSaving: boolean;

    taxclasses: TaxClass[];

    taxrates: TaxRate[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private taxRateService: TaxRateService,
        private taxClassService: TaxClassService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taxRate']);
        this.taxRate = new TaxRate();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.taxClassService.query().subscribe(
            (res: Response) => { this.taxclasses = res.json(); }, (res: Response) => this.onError(res.json()));
        this.taxRateService.query().subscribe(
            (res: Response) => { this.taxrates = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.taxRate.id !== undefined) {
            this.taxRateService.update(this.taxRate)
                .subscribe((res: TaxRate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.taxRateService.create(this.taxRate)
                .subscribe((res: TaxRate) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: TaxRate) {
        this.eventManager.broadcast({ name: 'taxRateListModification', content: 'OK'});
        this.isSaving = false;
        this.taxRate = result;
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

    trackTaxClassById(index: number, item: TaxClass) {
        return item.id;
    }

    trackTaxRateById(index: number, item: TaxRate) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tax-rate-popup',
    template: ''
})
export class TaxRatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taxRatePopupService: TaxRatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.taxRatePopupService
                    .open(TaxRateDialogComponent, params['id']);
            } else {
                this.modalRef = this.taxRatePopupService
                    .open(TaxRateDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
