import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CustomerOptionDescription } from './customer-option-description.model';
import { CustomerOptionDescriptionService } from './customer-option-description.service';

@Component({
    selector: 'jhi-customer-option-description-detail',
    templateUrl: './customer-option-description-detail.component.html'
})
export class CustomerOptionDescriptionDetailComponent implements OnInit, OnDestroy {

    customerOptionDescription: CustomerOptionDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerOptionDescriptionService: CustomerOptionDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['customerOptionDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerOptionDescriptions();
    }

    load(id) {
        this.customerOptionDescriptionService.find(id).subscribe((customerOptionDescription) => {
            this.customerOptionDescription = customerOptionDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.customerOptionDescription.id !== undefined) {
            this.customerOptionDescriptionService.update(this.customerOptionDescription)
                .subscribe((res: CustomerOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptionDescriptionService.create(this.customerOptionDescription)
                .subscribe((res: CustomerOptionDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptionDescription) {
        this.eventManager.broadcast({ name: 'customerOptionDescriptionModification', content: 'OK'});
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

    registerChangeInCustomerOptionDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('customerOptionDescriptionListModification', (response) => this.load(this.customerOptionDescription.id));
    }
}
