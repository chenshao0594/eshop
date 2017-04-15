import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderProductDownload } from './order-product-download.model';
import { OrderProductDownloadPopupService } from './order-product-download-popup.service';
import { OrderProductDownloadService } from './order-product-download.service';
import { OrderProduct, OrderProductService } from '../order-product';

@Component({
    selector: 'jhi-order-product-download-dialog',
    templateUrl: './order-product-download-dialog.component.html'
})
export class OrderProductDownloadDialogComponent implements OnInit {

    orderProductDownload: OrderProductDownload;
    authorities: any[];
    isSaving: boolean;

    orderproducts: OrderProduct[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderProductDownloadService: OrderProductDownloadService,
        private orderProductService: OrderProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderProductDownload']);
        this.orderProductDownload = new OrderProductDownload();
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
        if (this.orderProductDownload.id !== undefined) {
            this.orderProductDownloadService.update(this.orderProductDownload)
                .subscribe((res: OrderProductDownload) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductDownloadService.create(this.orderProductDownload)
                .subscribe((res: OrderProductDownload) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProductDownload) {
        this.eventManager.broadcast({ name: 'orderProductDownloadListModification', content: 'OK'});
        this.isSaving = false;
        this.orderProductDownload = result;
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
    selector: 'jhi-order-product-download-popup',
    template: ''
})
export class OrderProductDownloadPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderProductDownloadPopupService: OrderProductDownloadPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderProductDownloadPopupService
                    .open(OrderProductDownloadDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderProductDownloadPopupService
                    .open(OrderProductDownloadDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
