import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderAccount } from './order-account.model';
import { OrderAccountPopupService } from './order-account-popup.service';
import { OrderAccountService } from './order-account.service';
import { SalesOrder, SalesOrderService } from '../sales-order';

@Component({
    selector: 'jhi-order-account-dialog',
    templateUrl: './order-account-dialog.component.html'
})
export class OrderAccountDialogComponent implements OnInit {

    orderAccount: OrderAccount;
    authorities: any[];
    isSaving: boolean;

    salesorders: SalesOrder[];
            constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderAccountService: OrderAccountService,
        private salesOrderService: SalesOrderService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderAccount']);
        this.orderAccount = new OrderAccount();
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
        if (this.orderAccount.id !== undefined) {
            this.orderAccountService.update(this.orderAccount)
                .subscribe((res: OrderAccount) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderAccountService.create(this.orderAccount)
                .subscribe((res: OrderAccount) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderAccount) {
        this.eventManager.broadcast({ name: 'orderAccountListModification', content: 'OK'});
        this.isSaving = false;
        this.orderAccount = result;
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
    selector: 'jhi-order-account-popup',
    template: ''
})
export class OrderAccountPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderAccountPopupService: OrderAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderAccountPopupService
                    .open(OrderAccountDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderAccountPopupService
                    .open(OrderAccountDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
