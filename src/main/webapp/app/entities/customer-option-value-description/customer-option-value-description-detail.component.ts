import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CustomerOptionValueDescription } from './customer-option-value-description.model';
import { CustomerOptionValueDescriptionService } from './customer-option-value-description.service';

@Component({
    selector: 'jhi-customer-option-value-description-detail',
    templateUrl: './customer-option-value-description-detail.component.html'
})
export class CustomerOptionValueDescriptionDetailComponent implements OnInit, OnDestroy {

    customerOptionValueDescription: CustomerOptionValueDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerOptionValueDescriptionService: CustomerOptionValueDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['customerOptionValueDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerOptionValueDescriptions();
    }

    load(id) {
        this.customerOptionValueDescriptionService.find(id).subscribe((customerOptionValueDescription) => {
            this.customerOptionValueDescription = customerOptionValueDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.customerOptionValueDescription.id !== undefined) {
            this.customerOptionValueDescriptionService.update(this.customerOptionValueDescription)
                .subscribe((res: CustomerOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionValueDescriptionService.create(this.customerOptionValueDescription)
                .subscribe((res: CustomerOptionValueDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionValueDescription) {
        this.eventManager.broadcast({ name: 'customerOptionValueDescriptionModification', content: 'OK'});
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

    registerChangeInCustomerOptionValueDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('customerOptionValueDescriptionListModification', (response) => this.load(this.customerOptionValueDescription.id));
    }
}
