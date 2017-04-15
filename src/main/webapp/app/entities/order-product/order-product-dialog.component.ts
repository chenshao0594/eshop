import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderProduct } from './order-product.model';
import { OrderProductPopupService } from './order-product-popup.service';
import { OrderProductService } from './order-product.service';
import { SalesOrder, SalesOrderService } from '../sales-order';

@Component({
    selector: 'jhi-order-product-dialog',
    templateUrl: './order-product-dialog.component.html'
})
export class OrderProductDialogComponent implements OnInit {

    orderProduct: OrderProduct;
    authorities: any[];
    isSaving: boolean;

    salesorders: SalesOrder[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderProductService: OrderProductService,
        private salesOrderService: SalesOrderService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderProduct']);
        this.orderProduct = new OrderProduct();
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
        if (this.orderProduct.id !== undefined) {
            this.orderProductService.update(this.orderProduct)
                .subscribe((res: OrderProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductService.create(this.orderProduct)
                .subscribe((res: OrderProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProduct) {
        this.eventManager.broadcast({ name: 'orderProductListModification', content: 'OK'});
        this.isSaving = false;
        this.orderProduct = result;
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
    selector: 'jhi-order-product-popup',
    template: ''
})
export class OrderProductPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderProductPopupService: OrderProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderProductPopupService
                    .open(OrderProductDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderProductPopupService
                    .open(OrderProductDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
