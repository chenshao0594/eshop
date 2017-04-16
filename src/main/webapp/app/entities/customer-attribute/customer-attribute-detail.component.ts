import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CustomerAttribute } from './customer-attribute.model';
import { CustomerAttributeService } from './customer-attribute.service';

@Component({
    selector: 'jhi-customer-attribute-detail',
    templateUrl: './customer-attribute-detail.component.html'
})
export class CustomerAttributeDetailComponent implements OnInit, OnDestroy {

    customerAttribute: CustomerAttribute;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerAttributeService: CustomerAttributeService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['customerAttribute']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerAttributes();
    }

    load(id) {
        this.customerAttributeService.find(id).subscribe((customerAttribute) => {
            this.customerAttribute = customerAttribute;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.customerAttribute.id !== undefined) {
            this.customerAttributeService.update(this.customerAttribute)
                .subscribe((res: CustomerAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerAttributeService.create(this.customerAttribute)
                .subscribe((res: CustomerAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerAttribute) {
        this.eventManager.broadcast({ name: 'customerAttributeModification', content: 'OK'});
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

    registerChangeInCustomerAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('customerAttributeListModification', (response) => this.load(this.customerAttribute.id));
    }
}
