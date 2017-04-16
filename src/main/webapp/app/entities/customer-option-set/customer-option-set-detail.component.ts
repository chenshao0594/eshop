import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CustomerOptionSet } from './customer-option-set.model';
import { CustomerOptionSetService } from './customer-option-set.service';

@Component({
    selector: 'jhi-customer-option-set-detail',
    templateUrl: './customer-option-set-detail.component.html'
})
export class CustomerOptionSetDetailComponent implements OnInit, OnDestroy {

    customerOptionSet: CustomerOptionSet;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerOptionSetService: CustomerOptionSetService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['customerOptionSet']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerOptionSets();
    }

    load(id) {
        this.customerOptionSetService.find(id).subscribe((customerOptionSet) => {
            this.customerOptionSet = customerOptionSet;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.customerOptionSet.id !== undefined) {
            this.customerOptionSetService.update(this.customerOptionSet)
                .subscribe((res: CustomerOptionSet) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionSetService.create(this.customerOptionSet)
                .subscribe((res: CustomerOptionSet) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionSet) {
        this.eventManager.broadcast({ name: 'customerOptionSetModification', content: 'OK'});
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

    registerChangeInCustomerOptionSets() {
        this.eventSubscriber = this.eventManager.subscribe('customerOptionSetListModification', (response) => this.load(this.customerOptionSet.id));
    }
}
