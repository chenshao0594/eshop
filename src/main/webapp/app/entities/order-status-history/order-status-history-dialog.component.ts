import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderStatusHistory } from './order-status-history.model';
import { OrderStatusHistoryPopupService } from './order-status-history-popup.service';
import { OrderStatusHistoryService } from './order-status-history.service';
import { SalesOrder, SalesOrderService } from '../sales-order';

@Component({
    selector: 'jhi-order-status-history-dialog',
    templateUrl: './order-status-history-dialog.component.html'
})
export class OrderStatusHistoryDialogComponent implements OnInit {

    orderStatusHistory: OrderStatusHistory;
    authorities: any[];
    isSaving: boolean;

    salesorders: SalesOrder[];
        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderStatusHistoryService: OrderStatusHistoryService,
        private salesOrderService: SalesOrderService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderStatusHistory', 'orderStatus']);
        this.orderStatusHistory = new OrderStatusHistory();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.salesOrderService.query().subscribe(
            (res: Response) => { this.salesorders = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
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
        this.eventManager.broadcast({ name: 'orderStatusHistoryListModification', content: 'OK'});
        this.isSaving = false;
        this.orderStatusHistory = result;
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

    trackSalesOrderById(index: number, item: SalesOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-status-history-popup',
    template: ''
})
export class OrderStatusHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderStatusHistoryPopupService: OrderStatusHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderStatusHistoryPopupService
                    .open(OrderStatusHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderStatusHistoryPopupService
                    .open(OrderStatusHistoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
