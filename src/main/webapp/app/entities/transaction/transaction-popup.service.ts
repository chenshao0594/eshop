import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Transaction } from './transaction.model';
import { TransactionService } from './transaction.service';
@Injectable()
export class TransactionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private transactionService: TransactionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.transactionService.find(id).subscribe((transaction) => {
                if (transaction.transactionDate) {
                    transaction.transactionDate = {
                        year: transaction.transactionDate.getFullYear(),
                        month: transaction.transactionDate.getMonth() + 1,
                        day: transaction.transactionDate.getDate()
                    };
                }
                if (transaction.created_date) {
                    transaction.created_date = {
                        year: transaction.created_date.getFullYear(),
                        month: transaction.created_date.getMonth() + 1,
                        day: transaction.created_date.getDate()
                    };
                }
                if (transaction.last_modified_date) {
                    transaction.last_modified_date = {
                        year: transaction.last_modified_date.getFullYear(),
                        month: transaction.last_modified_date.getMonth() + 1,
                        day: transaction.last_modified_date.getDate()
                    };
                }
                this.transactionModalRef(component, transaction);
            });
        } else {
            return this.transactionModalRef(component, new Transaction());
        }
    }

    transactionModalRef(component: Component, transaction: Transaction): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.transaction = transaction;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
