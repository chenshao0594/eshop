import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderTotal } from './order-total.model';
import { OrderTotalPopupService } from './order-total-popup.service';
import { OrderTotalService } from './order-total.service';

@Component({
    selector: 'jhi-order-total-delete-dialog',
    templateUrl: './order-total-delete-dialog.component.html'
})
export class OrderTotalDeleteDialogComponent {

    orderTotal: OrderTotal;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderTotalService: OrderTotalService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderTotal', 'orderValueType', 'orderTotalType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderTotalService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderTotalListModification',
                content: 'Deleted an orderTotal'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-total-delete-popup',
    template: ''
})
export class OrderTotalDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderTotalPopupService: OrderTotalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.orderTotalPopupService
                .open(OrderTotalDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
