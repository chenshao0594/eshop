import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { TaxRate } from './tax-rate.model';
import { TaxRateService } from './tax-rate.service';

@Component({
    selector: 'jhi-tax-rate-detail',
    templateUrl: './tax-rate-detail.component.html'
})
export class TaxRateDetailComponent implements OnInit, OnDestroy {

    taxRate: TaxRate;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private taxRateService: TaxRateService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['taxRate']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTaxRates();
    }

    load(id) {
        this.taxRateService.find(id).subscribe((taxRate) => {
            this.taxRate = taxRate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTaxRates() {
        this.eventSubscriber = this.eventManager.subscribe('taxRateListModification', (response) => this.load(this.taxRate.id));
    }
}
