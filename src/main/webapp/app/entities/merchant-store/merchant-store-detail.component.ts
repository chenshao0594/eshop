import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { MerchantStore } from './merchant-store.model';
import { MerchantStorePopupService } from './merchant-store-popup.service';
import { MerchantStoreService } from './merchant-store.service';
import { Language, LanguageService } from '../language';
import { Zone, ZoneService } from '../zone';
import { Currency, CurrencyService } from '../currency';
import { Country, CountryService } from '../country';

@Component({
    selector: 'jhi-merchant-store-detail',
    templateUrl: './merchant-store-detail.component.html'
})
export class MerchantStoreDetailComponent implements OnInit, OnDestroy {

    merchantStore: MerchantStore;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;
    languages: Language[];
    zones: Zone[];
    currencies: Currency[];
    countries: Country[];
    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private merchantStoreService: MerchantStoreService,
        private route: ActivatedRoute,
        private alertService: AlertService,
        private languageService: LanguageService,
        private zoneService: ZoneService,
        private currencyService: CurrencyService,
        private countryService: CountryService,
    ) {
        this.jhiLanguageService.setLocations(['merchantStore']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.languageService.query().subscribe(
            (res: Response) => { this.languages = res.json(); }, (res: Response) => this.onError(res.json()));
        this.zoneService.query().subscribe(
            (res: Response) => { this.zones = res.json(); }, (res: Response) => this.onError(res.json()));
        this.currencyService.query().subscribe(
            (res: Response) => { this.currencies = res.json(); }, (res: Response) => this.onError(res.json()));
        this.countryService.query().subscribe(
            (res: Response) => { this.countries = res.json(); }, (res: Response) => this.onError(res.json()));
        this.registerChangeInMerchantStores();
    }

    load(id) {
        this.merchantStoreService.find(id).subscribe((merchantStore) => {
            this.merchantStore = merchantStore;
        });
    }
    previousState() {
        window.history.back();
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
        this.eventManager.broadcast({ name: 'merchantStoreModification', content: 'OK'});
        this.isSaving = false;
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMerchantStores() {
        this.eventSubscriber = this.eventManager.subscribe('merchantStoreListModification', (response) => this.load(this.merchantStore.id));
    }
}
