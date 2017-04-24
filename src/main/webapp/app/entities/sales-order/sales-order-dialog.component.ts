import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { SalesOrder } from './sales-order.model';
import { SalesOrderPopupService } from './sales-order-popup.service';
import { SalesOrderService } from './sales-order.service';
import { Currency, CurrencyService } from '../currency';
import { MerchantStore, MerchantStoreService } from '../merchant-store';

@Component({
    selector: 'jhi-sales-order-dialog',
    templateUrl: './sales-order-dialog.component.html'
})
export class SalesOrderDialogComponent implements OnInit {

    salesOrder: SalesOrder;
    authorities: any[];
    isSaving: boolean;

    currencies: Currency[];

    merchantstores: MerchantStore[];
                        constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private salesOrderService: SalesOrderService,
        private currencyService: CurrencyService,
        private merchantStoreService: MerchantStoreService,
        private eventManager: EventManager,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['salesOrder', 'paymentType', 'orderChannel', 'orderType', 'orderStatus']);
    }

    ngOnInit() {
        this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.load(params['id']);
            } else {
                this.salesOrder = new SalesOrder();
            }
        });
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.currencyService.query().subscribe(
            (res: Response) => { this.currencies = res.json(); }, (res: Response) => this.onError(res.json()));
        this.merchantStoreService.query().subscribe(
            (res: Response) => { this.merchantstores = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    load(id) {
         this.salesOrderService.find(id).subscribe((salesOrder) => {
                this.salesOrder = salesOrder;
                
         });
    }
    clear() {
       // this.activeModal.dismiss('cancel');
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
        this.eventManager.broadcast({ name: 'salesOrderListModification', content: 'OK'});
        this.isSaving = false;
      //  this.activeModal.dismiss(result);
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

    trackCurrencyById(index: number, item: Currency) {
        return item.id;
    }

    trackMerchantStoreById(index: number, item: MerchantStore) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sales-order-popup',
    template: ''
})
export class SalesOrderPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesOrderPopupService: SalesOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.salesOrderPopupService
                    .open(SalesOrderDialogComponent, params['id']);
            } else {
                this.modalRef = this.salesOrderPopupService
                    .open(SalesOrderDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
