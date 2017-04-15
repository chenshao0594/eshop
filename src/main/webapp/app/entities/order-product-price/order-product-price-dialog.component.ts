import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderProductPrice } from './order-product-price.model';
import { OrderProductPricePopupService } from './order-product-price-popup.service';
import { OrderProductPriceService } from './order-product-price.service';
import { OrderProduct, OrderProductService } from '../order-product';

@Component({
    selector: 'jhi-order-product-price-dialog',
    templateUrl: './order-product-price-dialog.component.html'
})
export class OrderProductPriceDialogComponent implements OnInit {

    orderProductPrice: OrderProductPrice;
    authorities: any[];
    isSaving: boolean;

    orderproducts: OrderProduct[];
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderProductPriceService: OrderProductPriceService,
        private orderProductService: OrderProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderProductPrice']);
        this.orderProductPrice = new OrderProductPrice();
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.orderProductService.query().subscribe(
            (res: Response) => { this.orderproducts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orderProductPrice.id !== undefined) {
            this.orderProductPriceService.update(this.orderProductPrice)
                .subscribe((res: OrderProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductPriceService.create(this.orderProductPrice)
                .subscribe((res: OrderProductPrice) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProductPrice) {
        this.eventManager.broadcast({ name: 'orderProductPriceListModification', content: 'OK'});
        this.isSaving = false;
        this.orderProductPrice = result;
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
}

@Component({
    selector: 'jhi-order-product-price-popup',
    template: ''
})
export class OrderProductPricePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderProductPricePopupService: OrderProductPricePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderProductPricePopupService
                    .open(OrderProductPriceDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderProductPricePopupService
                    .open(OrderProductPriceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
