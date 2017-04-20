import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Billing } from './billing.model';
import { BillingService } from './billing.service';

@Component({
    selector: 'jhi-billing-detail',
    templateUrl: './billing-detail.component.html'
})
export class BillingDetailComponent implements OnInit, OnDestroy {

    billing: Billing;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private billingService: BillingService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['billing']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBillings();
    }

    load(id) {
        this.billingService.find(id).subscribe((billing) => {
            this.billing = billing;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBillings() {
        this.eventSubscriber = this.eventManager.subscribe('billingListModification', (response) => this.load(this.billing.id));
    }
}
