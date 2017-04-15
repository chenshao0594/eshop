import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { OrderProductAttribute } from './order-product-attribute.model';
import { OrderProductAttributePopupService } from './order-product-attribute-popup.service';
import { OrderProductAttributeService } from './order-product-attribute.service';
import { OrderProduct, OrderProductService } from '../order-product';

@Component({
    selector: 'jhi-order-product-attribute-dialog',
    templateUrl: './order-product-attribute-dialog.component.html'
})
export class OrderProductAttributeDialogComponent implements OnInit {

    orderProductAttribute: OrderProductAttribute;
    authorities: any[];
    isSaving: boolean;

    orderproducts: OrderProduct[];
    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private orderProductAttributeService: OrderProductAttributeService,
        private orderProductService: OrderProductService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderProductAttribute']);
        this.orderProductAttribute = new OrderProductAttribute();
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
        if (this.orderProductAttribute.id !== undefined) {
            this.orderProductAttributeService.update(this.orderProductAttribute)
                .subscribe((res: OrderProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductAttributeService.create(this.orderProductAttribute)
                .subscribe((res: OrderProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProductAttribute) {
        this.eventManager.broadcast({ name: 'orderProductAttributeListModification', content: 'OK'});
        this.isSaving = false;
        this.orderProductAttribute = result;
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
    selector: 'jhi-order-product-attribute-popup',
    template: ''
})
export class OrderProductAttributePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderProductAttributePopupService: OrderProductAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.orderProductAttributePopupService
                    .open(OrderProductAttributeDialogComponent, params['id']);
            } else {
                this.modalRef = this.orderProductAttributePopupService
                    .open(OrderProductAttributeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
