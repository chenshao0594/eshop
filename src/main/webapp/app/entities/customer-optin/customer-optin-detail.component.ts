import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CustomerOptin } from './customer-optin.model';
import { CustomerOptinService } from './customer-optin.service';

@Component({
    selector: 'jhi-customer-optin-detail',
    templateUrl: './customer-optin-detail.component.html'
})
export class CustomerOptinDetailComponent implements OnInit, OnDestroy {

    customerOptin: CustomerOptin;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private customerOptinService: CustomerOptinService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['customerOptin']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerOptins();
    }

    load(id) {
        this.customerOptinService.find(id).subscribe((customerOptin) => {
            this.customerOptin = customerOptin;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.customerOptin.id !== undefined) {
            this.customerOptinService.update(this.customerOptin)
                .subscribe((res: CustomerOptin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.customerOptinService.create(this.customerOptin)
                .subscribe((res: CustomerOptin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CustomerOptin) {
        this.eventManager.broadcast({ name: 'customerOptinModification', content: 'OK'});
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

    registerChangeInCustomerOptins() {
        this.eventSubscriber = this.eventManager.subscribe('customerOptinListModification', (response) => this.load(this.customerOptin.id));
    }
}
