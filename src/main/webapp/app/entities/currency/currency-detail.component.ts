import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Currency } from './currency.model';
import { CurrencyService } from './currency.service';

@Component({
    selector: 'jhi-currency-detail',
    templateUrl: './currency-detail.component.html'
})
export class CurrencyDetailComponent implements OnInit, OnDestroy {

    currency: Currency;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private currencyService: CurrencyService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['currency']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCurrencies();
    }

    load(id) {
        this.currencyService.find(id).subscribe((currency) => {
            this.currency = currency;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.currency.id !== undefined) {
            this.currencyService.update(this.currency)
                .subscribe((res: Currency) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.currencyService.create(this.currency)
                .subscribe((res: Currency) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Currency) {
        this.eventManager.broadcast({ name: 'currencyModification', content: 'OK'});
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

    registerChangeInCurrencies() {
        this.eventSubscriber = this.eventManager.subscribe('currencyListModification', (response) => this.load(this.currency.id));
    }
}
