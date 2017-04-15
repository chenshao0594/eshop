import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderAccountProduct } from './order-account-product.model';
import { OrderAccountProductPopupService } from './order-account-product-popup.service';
import { OrderAccountProductService } from './order-account-product.service';

@Component({
    selector: 'jhi-order-account-product-delete-dialog',
    templateUrl: './order-account-product-delete-dialog.component.html'
})
export class OrderAccountProductDeleteDialogComponent {

    orderAccountProduct: OrderAccountProduct;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderAccountProductService: OrderAccountProductService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderAccountProduct']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderAccountProductService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderAccountProductListModification',
                content: 'Deleted an orderAccountProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-account-product-delete-popup',
    template: ''
})
export class OrderAccountProductDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderAccountProductPopupService: OrderAccountProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.orderAccountProductPopupService
                .open(OrderAccountProductDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
