import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { OrderAccount } from './order-account.model';
import { OrderAccountPopupService } from './order-account-popup.service';
import { OrderAccountService } from './order-account.service';

@Component({
    selector: 'jhi-order-account-delete-dialog',
    templateUrl: './order-account-delete-dialog.component.html'
})
export class OrderAccountDeleteDialogComponent {

    orderAccount: OrderAccount;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private orderAccountService: OrderAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['orderAccount']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderAccountListModification',
                content: 'Deleted an orderAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-account-delete-popup',
    template: ''
})
export class OrderAccountDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderAccountPopupService: OrderAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.orderAccountPopupService
                .open(OrderAccountDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
