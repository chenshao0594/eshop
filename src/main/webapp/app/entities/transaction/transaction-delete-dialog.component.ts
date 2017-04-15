import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Transaction } from './transaction.model';
import { TransactionPopupService } from './transaction-popup.service';
import { TransactionService } from './transaction.service';

@Component({
    selector: 'jhi-transaction-delete-dialog',
    templateUrl: './transaction-delete-dialog.component.html'
})
export class TransactionDeleteDialogComponent {

    transaction: Transaction;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private transactionService: TransactionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['transaction', 'transactionType', 'paymentType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transactionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'transactionListModification',
                content: 'Deleted an transaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transaction-delete-popup',
    template: ''
})
export class TransactionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionPopupService: TransactionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.transactionPopupService
                .open(TransactionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
