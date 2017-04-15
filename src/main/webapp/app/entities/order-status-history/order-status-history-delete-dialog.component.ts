import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderStatusHistory } from './order-status-history.model';
import { OrderStatusHistoryPopupService } from './order-status-history-popup.service';
import { OrderStatusHistoryService } from './order-status-history.service';

@Component({
    selector: 'jhi-order-status-history-delete-dialog',
    templateUrl: './order-status-history-delete-dialog.component.html'
})
export class OrderStatusHistoryDeleteDialogComponent {

    orderStatusHistory: OrderStatusHistory;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderStatusHistoryService: OrderStatusHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderStatusHistory', 'orderStatus']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderStatusHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderStatusHistoryListModification',
                content: 'Deleted an orderStatusHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-status-history-delete-popup',
    template: ''
})
export class OrderStatusHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderStatusHistoryPopupService: OrderStatusHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.orderStatusHistoryPopupService
                .open(OrderStatusHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
