import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderAccount } from './order-account.model';
import { OrderAccountService } from './order-account.service';

@Component({
    selector: 'jhi-order-account-detail',
    templateUrl: './order-account-detail.component.html'
})
export class OrderAccountDetailComponent implements OnInit, OnDestroy {

    orderAccount: OrderAccount;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderAccountService: OrderAccountService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderAccount']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderAccounts();
    }

    load(id) {
        this.orderAccountService.find(id).subscribe((orderAccount) => {
            this.orderAccount = orderAccount;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderAccount.id !== undefined) {
            this.orderAccountService.update(this.orderAccount)
                .subscribe((res: OrderAccount) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderAccountService.create(this.orderAccount)
                .subscribe((res: OrderAccount) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderAccount) {
        this.eventManager.broadcast({ name: 'orderAccountModification', content: 'OK'});
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

    registerChangeInOrderAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('orderAccountListModification', (response) => this.load(this.orderAccount.id));
    }
}
