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
                if (transaction.createdDate) {
                    transaction.createdDate = {
                        year: transaction.createdDate.getFullYear(),
                        month: transaction.createdDate.getMonth() + 1,
                        day: transaction.createdDate.getDate()
                    };
                }
                if (transaction.lastModifiedDate) {
                    transaction.lastModifiedDate = {
                        year: transaction.lastModifiedDate.getFullYear(),
                        month: transaction.lastModifiedDate.getMonth() + 1,
                        day: transaction.lastModifiedDate.getDate()
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
