import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderAccountProduct } from './order-account-product.model';
import { OrderAccountProductPopupService } from './order-account-product-popup.service';
import { OrderAccountProductService } from './order-account-product.service';
import { OrderProduct, OrderProductService } from '../order-product';
import { OrderAccount, OrderAccountService } from '../order-account';

@Component({
    selector: 'jhi-order-account-product-dialog',
    templateUrl: './order-account-product-dialog.component.html'
})
export class OrderAccountProductDialogComponent implements OnInit {

    orderAccountProduct: OrderAccountProduct;
    authorities: any[];
    isSaving: boolean;

    orderproducts: OrderProduct[];

    orderaccounts: OrderAccount[];
                        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderAccountProductService: OrderAccountProductService,
        private orderProductService: OrderProductService,
        private orderAccountService: OrderAccountService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderAccountProduct']);
        this.orderAccountProduct = new OrderAccountProduct();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.orderProductService.query().subscribe(
            (res: Response) => { this.orderproducts = res.json(); }, (res: Response) => this.onError(res.json()));
        this.orderAccountService.query().subscribe(
            (res: Response) => { this.orderaccounts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orderAccountProduct.id !== undefined) {
            this.orderAccountProductService.update(this.orderAccountProduct)
                .subscribe((res: OrderAccountProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderAccountProductService.create(this.orderAccountProduct)
                .subscribe((res: OrderAccountProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderAccountProduct) {
        this.eventManager.broadcast({ name: 'orderAccountProductListModification', content: 'OK'});
        this.isSaving = false;
        this.orderAccountProduct = result;
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

    trackOrderProductById(index: number, item: OrderProduct) {
        return item.id;
    }

    trackOrderAccountById(index: number, item: OrderAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-account-product-popup',
    template: ''
})
export class OrderAccountProductPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderAccountProductPopupService: OrderAccountProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderAccountProductPopupService
                    .open(OrderAccountProductDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderAccountProductPopupService
                    .open(OrderAccountProductDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
