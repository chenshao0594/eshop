import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CustomerOption } from './customer-option.model';
import { CustomerOptionService } from './customer-option.service';

@Component({
    selector: 'jhi-customer-option-detail',
    templateUrl: './customer-option-detail.component.html'
})
export class CustomerOptionDetailComponent implements OnInit, OnDestroy {

    customerOption: CustomerOption;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerOptionService: CustomerOptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['customerOption']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerOptions();
    }

    load(id) {
        this.customerOptionService.find(id).subscribe((customerOption) => {
            this.customerOption = customerOption;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.customerOption.id !== undefined) {
            this.customerOptionService.update(this.customerOption)
                .subscribe((res: CustomerOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionService.create(this.customerOption)
                .subscribe((res: CustomerOption) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOption) {
        this.eventManager.broadcast({ name: 'customerOptionModification', content: 'OK'});
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

    registerChangeInCustomerOptions() {
        this.eventSubscriber = this.eventManager.subscribe('customerOptionListModification', (response) => this.load(this.customerOption.id));
    }
}
