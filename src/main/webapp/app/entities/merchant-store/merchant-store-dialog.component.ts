import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MerchantStore } from './merchant-store.model';
import { MerchantStorePopupService } from './merchant-store-popup.service';
import { MerchantStoreService } from './merchant-store.service';
import { Language, LanguageService } from '../language';
import { Zone, ZoneService } from '../zone';
import { Currency, CurrencyService } from '../currency';
import { Country, CountryService } from '../country';

@Component({
    selector: 'jhi-merchant-store-dialog',
    templateUrl: './merchant-store-dialog.component.html'
})
export class MerchantStoreDialogComponent implements OnInit {

    merchantStore: MerchantStore;
    authorities: any[];
    isSaving: boolean;

    languages: Language[];

    zones: Zone[];

    currencies: Currency[];

    countries: Country[];
                constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private merchantStoreService: MerchantStoreService,
        private languageService: LanguageService,
        private zoneService: ZoneService,
        private currencyService: CurrencyService,
        private countryService: CountryService,
        private eventManager: EventManager,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['merchantStore']);
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.load(params['id']);
            } else {
                this.merchantStore = new MerchantStore();
            }
        });
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
        this.zoneService.query().subscribe(
            (res: Response) => { this.zones = res.json(); }, (res: Response) => this.onError(res.json()));
        this.currencyService.query().subscribe(
            (res: Response) => { this.currencies = res.json(); }, (res: Response) => this.onError(res.json()));
        this.countryService.query().subscribe(
            (res: Response) => { this.countries = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    load(id) {
         this.merchantStoreService.find(id).subscribe((merchantStore) => {
                this.merchantStore = merchantStore;
                
         });
    }
    clear() {
       // this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.merchantStore.id !== undefined) {
            this.merchantStoreService.update(this.merchantStore)
                .subscribe((res: MerchantStore) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.merchantStoreService.create(this.merchantStore)
                .subscribe((res: MerchantStore) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MerchantStore) {
        this.eventManager.broadcast({ name: 'merchantStoreListModification', content: 'OK'});
        this.isSaving = false;
      //  this.activeModal.dismiss(result);
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

    trackLanguageById(index: number, item: Language) {
        return item.id;
    }

    trackZoneById(index: number, item: Zone) {
        return item.id;
    }

    trackCurrencyById(index: number, item: Currency) {
        return item.id;
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-merchant-store-popup',
    template: ''
})
export class MerchantStorePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantStorePopupService: MerchantStorePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.merchantStorePopupService
                    .open(MerchantStoreDialogComponent, params['id']);
            } else {
                this.modalRef = this.merchantStorePopupService
                    .open(MerchantStoreDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
