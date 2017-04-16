import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { TaxRateDescription } from './tax-rate-description.model';
import { TaxRateDescriptionService } from './tax-rate-description.service';

@Component({
    selector: 'jhi-tax-rate-description-detail',
    templateUrl: './tax-rate-description-detail.component.html'
})
export class TaxRateDescriptionDetailComponent implements OnInit, OnDestroy {

    taxRateDescription: TaxRateDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private taxRateDescriptionService: TaxRateDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['taxRateDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTaxRateDescriptions();
    }

    load(id) {
        this.taxRateDescriptionService.find(id).subscribe((taxRateDescription) => {
            this.taxRateDescription = taxRateDescription;
        });
    }
    previousState() {
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
        this.eventManager.broadcast({ name: 'taxRateDescriptionModification', content: 'OK'});
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

    registerChangeInTaxRateDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('taxRateDescriptionListModification', (response) => this.load(this.taxRateDescription.id));
    }
}
