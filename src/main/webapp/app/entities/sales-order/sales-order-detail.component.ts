import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private salesOrderService: SalesOrderService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSalesOrders() {
        this.eventSubscriber = this.eventManager.subscribe('salesOrderListModification', (response) => this.load(this.salesOrder.id));
    }
}
