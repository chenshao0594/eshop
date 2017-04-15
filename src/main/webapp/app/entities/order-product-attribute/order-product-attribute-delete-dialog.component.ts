import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderProductAttribute } from './order-product-attribute.model';
import { OrderProductAttributePopupService } from './order-product-attribute-popup.service';
import { OrderProductAttributeService } from './order-product-attribute.service';

@Component({
    selector: 'jhi-order-product-attribute-delete-dialog',
    templateUrl: './order-product-attribute-delete-dialog.component.html'
})
export class OrderProductAttributeDeleteDialogComponent {

    orderProductAttribute: OrderProductAttribute;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderProductAttributeService: OrderProductAttributeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderProductAttribute']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderProductAttributeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderProductAttributeListModification',
                content: 'Deleted an orderProductAttribute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-product-attribute-delete-popup',
    template: ''
})
export class OrderProductAttributeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderProductAttributePopupService: OrderProductAttributePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.orderProductAttributePopupService
                .open(OrderProductAttributeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
