import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderTotal } from './order-total.model';
import { OrderTotalPopupService } from './order-total-popup.service';
import { OrderTotalService } from './order-total.service';
import { SalesOrder, SalesOrderService } from '../sales-order';

@Component({
    selector: 'jhi-order-total-dialog',
    templateUrl: './order-total-dialog.component.html'
})
export class OrderTotalDialogComponent implements OnInit {

    orderTotal: OrderTotal;
    authorities: any[];
    isSaving: boolean;

    salesorders: SalesOrder[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderTotalService: OrderTotalService,
        private salesOrderService: SalesOrderService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderTotal', 'orderValueType', 'orderTotalType']);
        this.orderTotal = new OrderTotal();
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
        this.eventManager.broadcast({ name: 'orderTotalListModification', content: 'OK'});
        this.isSaving = false;
        this.orderTotal = result;
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
    selector: 'jhi-order-total-popup',
    template: ''
})
export class OrderTotalPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderTotalPopupService: OrderTotalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderTotalPopupService
                    .open(OrderTotalDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderTotalPopupService
                    .open(OrderTotalDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
