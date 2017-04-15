import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CustomerOptionValue } from './customer-option-value.model';
import { CustomerOptionValueService } from './customer-option-value.service';

@Component({
    selector: 'jhi-customer-option-value-detail',
    templateUrl: './customer-option-value-detail.component.html'
})
export class CustomerOptionValueDetailComponent implements OnInit, OnDestroy {

    customerOptionValue: CustomerOptionValue;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerOptionValueService: CustomerOptionValueService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['customerOptionValue']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerOptionValues();
    }

    load(id) {
        this.customerOptionValueService.find(id).subscribe((customerOptionValue) => {
            this.customerOptionValue = customerOptionValue;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.customerOptionValue.id !== undefined) {
            this.customerOptionValueService.update(this.customerOptionValue)
                .subscribe((res: CustomerOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionValueService.create(this.customerOptionValue)
                .subscribe((res: CustomerOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionValue) {
        this.eventManager.broadcast({ name: 'customerOptionValueModification', content: 'OK'});
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

    registerChangeInCustomerOptionValues() {
        this.eventSubscriber = this.eventManager.subscribe('customerOptionValueListModification', (response) => this.load(this.customerOptionValue.id));
    }
}
