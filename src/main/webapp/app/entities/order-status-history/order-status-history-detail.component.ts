import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderStatusHistory } from './order-status-history.model';
import { OrderStatusHistoryService } from './order-status-history.service';

@Component({
    selector: 'jhi-order-status-history-detail',
    templateUrl: './order-status-history-detail.component.html'
})
export class OrderStatusHistoryDetailComponent implements OnInit, OnDestroy {

    orderStatusHistory: OrderStatusHistory;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderStatusHistoryService: OrderStatusHistoryService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderStatusHistory', 'orderStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderStatusHistories();
    }

    load(id) {
        this.orderStatusHistoryService.find(id).subscribe((orderStatusHistory) => {
            this.orderStatusHistory = orderStatusHistory;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderStatusHistory.id !== undefined) {
            this.orderStatusHistoryService.update(this.orderStatusHistory)
                .subscribe((res: OrderStatusHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderStatusHistoryService.create(this.orderStatusHistory)
                .subscribe((res: OrderStatusHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderStatusHistory) {
        this.eventManager.broadcast({ name: 'orderStatusHistoryModification', content: 'OK'});
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

    registerChangeInOrderStatusHistories() {
        this.eventSubscriber = this.eventManager.subscribe('orderStatusHistoryListModification', (response) => this.load(this.orderStatusHistory.id));
    }
}
