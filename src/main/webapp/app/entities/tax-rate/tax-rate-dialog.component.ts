import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { TaxRate } from './tax-rate.model';
import { TaxRatePopupService } from './tax-rate-popup.service';
import { TaxRateService } from './tax-rate.service';
import { Country, CountryService } from '../country';
import { TaxClass, TaxClassService } from '../tax-class';
import { MerchantStore, MerchantStoreService } from '../merchant-store';
import { Zone, ZoneService } from '../zone';

@Component({
    selector: 'jhi-tax-rate-dialog',
    templateUrl: './tax-rate-dialog.component.html'
})
export class TaxRateDialogComponent implements OnInit {

    taxRate: TaxRate;
    authorities: any[];
    isSaving: boolean;

    countries: Country[];

    taxrates: TaxRate[];

    taxclasses: TaxClass[];

    merchantstores: MerchantStore[];

    zones: Zone[];
            constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private taxRateService: TaxRateService,
        private countryService: CountryService,
        private taxClassService: TaxClassService,
        private merchantStoreService: MerchantStoreService,
        private zoneService: ZoneService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['taxRate']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.countryService.query().subscribe(
            (res: Response) => { this.countries = res.json(); }, (res: Response) => this.onError(res.json()));
        this.taxRateService.query().subscribe(
            (res: Response) => { this.taxrates = res.json(); }, (res: Response) => this.onError(res.json()));
        this.taxClassService.query().subscribe(
            (res: Response) => { this.taxclasses = res.json(); }, (res: Response) => this.onError(res.json()));
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
        this.zoneService.query({filter: 'zone-is-null'}).subscribe((res: Response) => {
            if (!this.taxRate.zone || !this.taxRate.zone.id) {
                this.zones = res.json();
            } else {
                this.zoneService.find(this.taxRate.zone.id).subscribe((subRes: Zone) => {
                    this.zones = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
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
        this.activeModal.dismiss(result);
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

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    trackTaxRateById(index: number, item: TaxRate) {
        return item.id;
    }

    trackTaxClassById(index: number, item: TaxClass) {
        return item.id;
    }

    trackMerchantStoreById(index: number, item: MerchantStore) {
        return item.id;
    }

    trackZoneById(index: number, item: Zone) {
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
