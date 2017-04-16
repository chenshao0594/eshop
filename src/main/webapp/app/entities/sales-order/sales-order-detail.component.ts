import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { SalesOrder } from './sales-order.model';
import { SalesOrderService } from './sales-order.service';

@Component({
    selector: 'jhi-sales-order-detail',
    templateUrl: './sales-order-detail.component.html'
})
export class SalesOrderDetailComponent implements OnInit, OnDestroy {

    salesOrder: SalesOrder;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private salesOrderService: SalesOrderService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['salesOrder', 'paymentType', 'orderChannel', 'orderType', 'orderStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSalesOrders();
    }

    load(id) {
        this.salesOrderService.find(id).subscribe((salesOrder) => {
            this.salesOrder = salesOrder;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.salesOrder.id !== undefined) {
            this.salesOrderService.update(this.salesOrder)
                .subscribe((res: SalesOrder) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.salesOrderService.create(this.salesOrder)
                .subscribe((res: SalesOrder) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: SalesOrder) {
        this.eventManager.broadcast({ name: 'salesOrderModification', content: 'OK'});
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

    registerChangeInSalesOrders() {
        this.eventSubscriber = this.eventManager.subscribe('salesOrderListModification', (response) => this.load(this.salesOrder.id));
    }
}
