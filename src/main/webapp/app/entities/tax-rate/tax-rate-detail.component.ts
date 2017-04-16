import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

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
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private taxRateService: TaxRateService,
        private route: ActivatedRoute,
        private alertService: AlertService
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
        this.eventManager.broadcast({ name: 'taxRateModification', content: 'OK'});
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

    registerChangeInTaxRates() {
        this.eventSubscriber = this.eventManager.subscribe('taxRateListModification', (response) => this.load(this.taxRate.id));
    }
}
