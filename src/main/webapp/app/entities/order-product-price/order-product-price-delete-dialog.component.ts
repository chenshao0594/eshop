import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderProductPrice } from './order-product-price.model';
import { OrderProductPricePopupService } from './order-product-price-popup.service';
import { OrderProductPriceService } from './order-product-price.service';

@Component({
    selector: 'jhi-order-product-price-delete-dialog',
    templateUrl: './order-product-price-delete-dialog.component.html'
})
export class OrderProductPriceDeleteDialogComponent {

    orderProductPrice: OrderProductPrice;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderProductPriceService: OrderProductPriceService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderProductPrice']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderProductPriceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderProductPriceListModification',
                content: 'Deleted an orderProductPrice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-product-price-delete-popup',
    template: ''
})
export class OrderProductPriceDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderProductPricePopupService: OrderProductPricePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.orderProductPricePopupService
                .open(OrderProductPriceDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
