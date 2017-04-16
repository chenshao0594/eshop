import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderTotal } from './order-total.model';
import { OrderTotalService } from './order-total.service';

@Component({
    selector: 'jhi-order-total-detail',
    templateUrl: './order-total-detail.component.html'
})
export class OrderTotalDetailComponent implements OnInit, OnDestroy {

    orderTotal: OrderTotal;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderTotalService: OrderTotalService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderTotal', 'orderValueType', 'orderTotalType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderTotals();
    }

    load(id) {
        this.orderTotalService.find(id).subscribe((orderTotal) => {
            this.orderTotal = orderTotal;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderTotal.id !== undefined) {
            this.orderTotalService.update(this.orderTotal)
                .subscribe((res: OrderTotal) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderTotalService.create(this.orderTotal)
                .subscribe((res: OrderTotal) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderTotal) {
        this.eventManager.broadcast({ name: 'orderTotalModification', content: 'OK'});
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

    registerChangeInOrderTotals() {
        this.eventSubscriber = this.eventManager.subscribe('orderTotalListModification', (response) => this.load(this.orderTotal.id));
    }
}
